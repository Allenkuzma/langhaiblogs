package cc.langhai.service.impl;

import cc.langhai.config.constant.ArticleConstant;
import cc.langhai.domain.*;
import cc.langhai.dto.ArticleDTO;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.ArticleMapper;
import cc.langhai.mapper.LabelMapper;
import cc.langhai.mq.config.MqConstants;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.service.*;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.UserContext;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * article service 实现类
 *
 * @author langhai
 * @date 2022-12-24 16:10
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelService labelService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private IArticleCommentService articleCommentService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long issue(ArticleDTO articleDTO) {
        Long userId = UserContext.getUserId();
        // 当天发布文章次数限制
        String nowDay = DateUtil.getNowDay();
        Integer dayCount = articleMapper.getDayCount(userId, nowDay + " 00:00:00",
                nowDay + " 23:59:59");
        if(dayCount >= ArticleConstant.ARTICLE_COUNT_TODAY){
            throw new BusinessException(ArticleReturnCode.ARTICLE_ISSUE_COUNT_DAY_FAIL_00002);
        }
        // 文章访问密码处理
        this.handleArticlePassword(articleDTO);
        // 标签处理
        Label labelMysql = this.labelHandle(articleDTO.getLabel(), articleDTO.getContent());
        // 将文章保存到数据库
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setUserId(userId);
        article.setLabelId(labelMysql.getId());
        article.setPublicShow("on".equals(articleDTO.getPublicShow()) ? 1 : 0);
        article.setDeleteFlag(0);
        article.setAddTime(new Date());
        // 置顶设置
        Boolean determineAdmin = roleService.determineAdmin();
        if (!determineAdmin) {
            article.setTopFlag(0);
        }
        // 审核状态
        if (!determineAdmin) {
            article.setCheckFlag(0);
        } else {
            article.setCheckFlag(1);
        }
        articleMapper.insertArticle(article);
        // 公开的文章
        if (article.getPublicShow().equals(1) && article.getCheckFlag().equals(1)) {
            // 利用消息队列发送消息 同步到es搜索引擎 这一步是可选操作
            rabbitTemplate.convertAndSend(MqConstants.BLOGS_EXCHANGE, MqConstants.BLOGS_INSERT_KEY, article.getId());
        }
        return article.getId();
    }

    @Override
    public List<Article> getAllArticle(String title, String abstractText, String param) {
        Long userId = UserContext.getUserId();
        if ("system".equals(param)) {
            userId = null;
        }
        List<Article> allArticle = articleMapper.getAllArticle(userId, title, abstractText);
        return allArticle;
    }

    @Override
    public Article getById(Long id) {
        Article article = articleMapper.getById(id);
        return article;
    }

    @Override
    public List<Article> getArticleHeat(List<Article> articleList) {
        if(CollectionUtil.isNotEmpty(articleList)){
            for (Article article : articleList) {
                // 获取文章热度值
                String heat = redisTemplate.opsForValue().get("article" + article.getAuthor() + article.getId());

                if(StrUtil.isBlank(heat)){
                    // 存储到redis当中
                    redisTemplate.opsForValue().set("article" + article.getAuthor() + article.getId(),
                            "1", 7 * 24 * 60, TimeUnit.MINUTES);
                    article.setHeat("1");
                }else {
                    article.setHeat(heat);
                }
            }
        }

        return articleList;
    }

    @Override
    public Article getArticleHeat(Article article) {
        if (ObjectUtil.isNotNull(article)) {
            String heat = redisTemplate.opsForValue().get("article" + article.getAuthor() + article.getId());
            if (StrUtil.isBlank(heat)) {
                // 存储到redis当中
                redisTemplate.opsForValue().set("article" + article.getAuthor() + article.getId(),
                        "1", 7 * 24 * 60, TimeUnit.MINUTES);
                article.setHeat("1");
            }else {
                Long heatLater = Long.parseLong(heat) + 1L;
                redisTemplate.opsForValue().set("article" + article.getAuthor() + article.getId(),
                        heatLater.toString(), 7 * 24 * 60, TimeUnit.MINUTES);
                article.setHeat(heatLater.toString());
            }
        }
        // 处理热点排名前十的文章
        if (article.getPublicShow().equals(1)) {
            this.handleHeatTopSet(article);
        }
        return article;
    }

    @Override
    public boolean judgeShow(HttpSession session, Article article) {
        // 文章是否审核通过
        if (!Integer.valueOf(1).equals(article.getCheckFlag())) {
            return false;
        }
        // 文章如果是公开的，则直接放行。
        if (Integer.valueOf(1).equals(article.getPublicShow())) {
            return true;
        }
        // 文章如果是不公开的，则需要判断是否是作者本人。
        User user = (User) session.getAttribute("user");
        if (ObjectUtil.isNull(user)) {
            return false;
        }
        return article.getUserId().equals(user.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleDTO articleDTO) {
        // 更新的文章id不能为空
        if (ObjectUtil.isNull(articleDTO.getId())) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_UPDATE_PARAM_FAIL_00004);
        }
        // 文章访问密码处理
        this.handleArticlePassword(articleDTO);
        // 文章是否有权限操作
        Article article = this.articlePermission(articleDTO.getId());
        // 置顶 审核状态处理
        Integer topFlag = article.getTopFlag();
        // 标签处理
        Label labelMysql = this.labelHandle(articleDTO.getLabel(), articleDTO.getContent());
        // 将文章更新到数据库
        BeanUtils.copyProperties(articleDTO, article);
        article.setLabelId(labelMysql.getId());
        article.setPublicShow("on".equals(articleDTO.getPublicShow()) ? 1 : 0);
        article.setUpdateTime(new Date());
        article.setTopFlag(topFlag);
        Boolean determineAdmin = roleService.determineAdmin();
        // 审核状态
        if (!determineAdmin) {
            article.setCheckFlag(0);
        } else {
            article.setCheckFlag(1);
        }
        articleMapper.updateArticle(article);
        // 利用消息队列发送消息 同步到es搜索引擎 这一步是可选操作
        rabbitTemplate.convertAndSend(MqConstants.BLOGS_EXCHANGE, MqConstants.BLOGS_INSERT_KEY, article.getId());
    }

    @Override
    public void deleteArticle(Long id) {
        // 文章是否有权限操作
        Article article = this.articlePermission(id);
        // 对文章进行逻辑删除
        article.setDeleteFlag(1);
        article.setUpdateTime(new Date());
        articleMapper.deleteArticle(article);
        if (article.getPublicShow().equals(1)) {
            // 利用消息队列发送消息 同步到es搜索引擎 这一步是可选操作
            rabbitTemplate.convertAndSend(MqConstants.BLOGS_EXCHANGE, MqConstants.BLOGS_DELETE_KEY, article.getId());
        }
    }

    @Override
    public PageInfo<Article> search(Integer page, Integer size, String searchArticleStr, Long labelId) {
        // 开启分页助手
        PageHelper.startPage(page, size);
        List<Article> allArticlePublicShow = articleMapper.getAllArticlePublicShow(searchArticleStr, labelId);
        PageInfo<Article> pageInfo = new PageInfo<>(allArticlePublicShow);
        return pageInfo;
    }

    @Override
    public HashMap<String, Object> searchES(Integer page, Integer size, String searchArticleStr) throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("langhaiblogs");
        // 2.准备DSL
        // 2.1.query
        if(StrUtil.isNotBlank(searchArticleStr)){
            request.source()
                    .query(QueryBuilders.multiMatchQuery(searchArticleStr, "title", "author", "labelContent"));
        }else {
            request.source()
                    .query(QueryBuilders.matchAllQuery());
        }
        // 2.2.分页 from、size
        request.source().from((page - 1) * size).size(size);
        // 3.发送请求
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        return handleResponse(response, size);
    }

    /**
     * 处理搜索结果
     *
     * @param response
     */
    private HashMap<String, Object> handleResponse(SearchResponse response, Integer size) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<Article> articles = new ArrayList<>();

        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1.获取总条数
        long total = searchHits.getTotalHits().value;
        // 4.2.文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        for (SearchHit hit : hits) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Article article = JSON.parseObject(json, Article.class);
            articles.add(article);
        }

        hashMap.put("list", articles);
        hashMap.put("pages", (total + size - 1) / size);
        return hashMap;
    }

    @Override
    public Article articlePermission(Long id) {
        // 文章id不能为空
        if (ObjectUtil.isNull(id)) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_PARAM_FAIL_00006);
        }
        Article article = articleMapper.getById(id);
        if (ObjectUtil.isNull(article)) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_PARAM_FAIL_00006);
        }
        // 文章是否有权限操作
        if (!article.getUserId().equals(UserContext.getUserId())) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_PERMISSION_FAIL_00007);
        }
        return article;
    }

    @Override
    public Set<Article> getArticleHeatTop() {
        // 旧版：遍历所有公开的文章
        /*List<Article> allArticlePublicShow = articleMapper.getAllArticlePublicShow("", null);
        List<Article> articleHeat = this.getArticleHeat(allArticlePublicShow);
        if(CollectionUtil.isNotEmpty(articleHeat)){
            // 直接使用List集合sort方法按照热度排序，
            articleHeat.sort((a, b) -> b.getHeat().compareTo(a.getHeat()));
            return articleHeat.stream().limit(10).collect(Collectors.toList());
        }*/

        return ArticleConstant.ARTICLE_HEAT_TOP_10;
    }

    @Override
    public void systemDeleteArticle(Long id) {
        // 文章id不能为空
        if(ObjectUtil.isNull(id)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_PARAM_FAIL_00006);
        }

        Article article = articleMapper.getById(id);
        if(ObjectUtil.isNull(article)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_PARAM_FAIL_00006);
        }
        articleMapper.systemDeleteArticle(id);

        if(article.getPublicShow().equals(1)){
            // 利用消息队列发送消息 同步到es搜索引擎 这一步是可选操作
            rabbitTemplate.convertAndSend(MqConstants.BLOGS_EXCHANGE, MqConstants.BLOGS_DELETE_KEY, article.getId());
        }
    }

    @Override
    public void submitComment(Long articleId, String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (ObjectUtil.isNull(user)) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_SUBMIT_COMMENT_USER_FAIL_00014);
        }
        // 参数合法校验
        if (ObjectUtil.isNull(articleId) || StrUtil.isBlank(content)) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_SUBMIT_COMMENT_PARAM_FAIL_00013);
        }
        // 当前用户对此篇文章只能评价三条
        List<ArticleComment> list = articleCommentService.list(Wrappers.<ArticleComment>lambdaQuery()
                .eq(ArticleComment::getUserId, user.getId())
                .eq(ArticleComment::getArticleId, articleId));
        if (list.size() >= ArticleConstant.ARTICLE_COMMENT_USER_COUNT) {
            throw new BusinessException(ArticleReturnCode.ARTICLE_SUBMIT_COMMENT_COUNT_FAIL_00015);
        }
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticleId(articleId);
        articleComment.setContent(content);
        articleComment.setUserId(user.getId());
        articleCommentService.save(articleComment);
    }

    @Override
    public List<Article> topArticle(Integer page, String searchArticleStr, Long labelId) {
        if (Integer.valueOf(1).equals(page) && StrUtil.isBlank(searchArticleStr)) {
            if (Long.valueOf(0L).equals(labelId) || ObjectUtil.isNull(labelId)) {
                List<Article> topArticleList = articleMapper.topArticle();
                if (topArticleList.size() >= 3) {
                    return topArticleList.subList(0, 3);
                }
                return topArticleList;
            }
        }
        return null;
    }

    @Override
    public void top(Long id, Boolean topFlag) {
        Article article = this.getById(id);
        if (ObjectUtil.isNotNull(article)) {
            article.setTopFlag(topFlag ? 1 : 0);
            article.setUpdateTime(new Date());
            articleMapper.updateArticle(article);
        }
    }

    @Override
    public void check(Long id, Boolean checkFlag) {
        Article article = this.getById(id);
        if (ObjectUtil.isNotNull(article)) {
            article.setCheckFlag(checkFlag ? 1 : 0);
            article.setUpdateTime(new Date());
            articleMapper.updateArticle(article);
            // 利用消息队列发送消息 同步到es搜索引擎 这一步是可选操作
            rabbitTemplate.convertAndSend(MqConstants.BLOGS_EXCHANGE, MqConstants.BLOGS_INSERT_KEY, article.getId());
        }
    }

    /**
     * 标签处理 判断是否要新增标签 还是使用原来的标签
     *
     * @param label 下拉框选择标签
     * @param content 新增标签内容
     * @return 标签实体类
     */
    private Label labelHandle(String label, String content){
        Long userId = UserContext.getUserId();

        // 判断标签是新增还是使用原来的标签
        if(StrUtil.isBlank(label) && StrUtil.isBlank(content)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_LABEL_PARAM_NULL_FAIL_00009);
        }

        if(StrUtil.isBlank(content) && "直接选择或搜索选择".equals(label)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_LABEL_PARAM_NULL_FAIL_00009);
        }

        Label labelMysql = null;
        // 新增标签
        if(StrUtil.isNotBlank(content)){
            labelMysql = labelService.verifyAddLabel(content);
        }

        // 使用原来的标签
        if(StrUtil.isBlank(content) && StrUtil.isNotBlank(label)){
            labelMysql = labelMapper.getLabelByUserAndContent(userId, label);
        }

        return labelMysql;
    }

    /**
     * 处理热点前十的文章排序集合Set
     *
     * @param articleSave 文章
     */
    private void handleHeatTopSet(Article articleSave) {
        TreeSet<Article> articleHeatTop10 = ArticleConstant.ARTICLE_HEAT_TOP_10;
        // 删除已经存在的文章
        if (CollectionUtil.isNotEmpty(articleHeatTop10)) {
            Iterator<Article> iterator = articleHeatTop10.iterator();
            while (iterator.hasNext()) {
                if (articleSave.getId().equals(iterator.next().getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        // 添加到set集合
        articleHeatTop10.add(articleSave);
        // 只存储十篇文章
        if (articleHeatTop10.size() > 10) {
            TreeSet<Article> articleTreeSet = new TreeSet<>();
            for (Article article : articleHeatTop10) {
                if (articleTreeSet.size() < 10) {
                    articleTreeSet.add(article);
                }
            }
            ArticleConstant.ARTICLE_HEAT_TOP_10 = articleTreeSet;
        }
    }

    /**
     * 处理文章访问密码长度
     *
     * @param articleDTO 文章传输DTO对象
     */
    private void handleArticlePassword(ArticleDTO articleDTO){
        if(StrUtil.isNotBlank(articleDTO.getPassword())){
            // 长度必须为六个字符
            if(ArticleConstant.ARTICLE_PASSWORD_LENGTH != articleDTO.getPassword().length()){
                throw new BusinessException(ArticleReturnCode.ARTICLE_PASSWORD_PARAM_LENGTH_FAIL_00010);
            }
        }
    }

}
