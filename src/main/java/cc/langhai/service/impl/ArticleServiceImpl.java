package cc.langhai.service.impl;

import cc.langhai.config.constant.ArticleConstant;
import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.ArticleMapper;
import cc.langhai.mapper.LabelMapper;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.service.ArticleService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void issue(String title, String content, String publicShow, String html, String label) {
        // 标题不能为空 文章内容不能为空
        if(StrUtil.isBlank(title) || StrUtil.isBlank(html)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_ISSUE_PARAM_FAIL_00001);
        }

        Long userId = UserContext.getUserId();

        // 当天发布文章次数限制
        String nowDay = DateUtil.getNowDay();
        Integer dayCount = articleMapper.getDayCount(userId, nowDay + " 00:00:00",
                nowDay + " 24:00:00");

        if(dayCount >= ArticleConstant.ARTICLE_COUNT_TODAY){
            throw new BusinessException(ArticleReturnCode.ARTICLE_ISSUE_COUNT_DAY_FAIL_00002);
        }

        // 判断标签是新增还是使用原来的标签
        if(StrUtil.isBlank(label) && StrUtil.isBlank(content)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_ISSUE_PARAM_FAIL_00001);
        }

        if(StrUtil.isBlank(content) && "直接选择或搜索选择".equals(label)){
            throw new BusinessException(ArticleReturnCode.ARTICLE_ISSUE_PARAM_FAIL_00001);
        }

        Label labelMysql = null;
        // 新增标签
        if(StrUtil.isNotBlank(content)){
            labelMysql = new Label();
            labelMysql.setUserId(userId);
            labelMysql.setAddTime(new Date());
            labelMysql.setContent(content);
            labelMapper.insertLabel(labelMysql);
        }

        // 使用原来的标签
        if(StrUtil.isBlank(content) && StrUtil.isNotBlank(label)){
            labelMysql = labelMapper.getLabelByUserAndContent(userId, label);
        }

        // 将文章保存到数据库
        Article article = new Article();
        article.setUserId(userId);
        article.setLabelId(labelMysql.getId());
        article.setTitle(title);
        article.setHtml(html);
        article.setPublicShow("on".equals(publicShow) ? 1 : 0);
        article.setDeleteFlag(0);
        article.setAddTime(new Date());
        articleMapper.insertArticle(article);
    }
}
