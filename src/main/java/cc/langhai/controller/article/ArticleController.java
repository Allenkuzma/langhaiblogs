package cc.langhai.controller.article;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.domain.User;
import cc.langhai.dto.ArticleDTO;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 文章控制器
 *
 * @author langhai
 * @date 2022-12-22 22:22
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private IArticleCommentService articleCommentService;

    @Autowired
    private RegisterService registerService;

    /**
     * 跳转到文章新发布页面
     *
     * @return 文章新发布页面
     */
    @GetMapping("/newArticlePage")
    public String newArticlePage(Model model){
        // 获取用户的所有标签内容
        List<String> labelContent = labelService.getAllLabelContentByUser();
        model.addAttribute("labelList", labelContent);
        return "blogs/article/newArticle";
    }

    /**
     * 场景：用户发布文章，保存到数据库。
     *
     * @return 数据 200代表成功 其他代表失败
     */
    @ResponseBody
    @PostMapping("/issue")
    public ResultResponse issue(@RequestBody @Validated ArticleDTO articleDTO){
        articleService.issue(articleDTO);

        return ResultResponse.success(ArticleReturnCode.ARTICLE_ISSUE_OK_00000);
    }

    /**
     * 跳转到文章列表管理页面
     *
     * @return 返回文章列表管理页面
     */
    @GetMapping("/articleListPage")
    public String articleListPage(){

        return "blogs/article/articleList";
    }

    /**
     * 获取用户文章列表页面数据
     * 场景：用户自己管理自己的文章
     *
     * @return 文章列表
     */
    @ResponseBody
    @GetMapping("/articleList")
    public JSONObject articleList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  String title, String abstractText){
        // 开启分页助手
        PageHelper.startPage(page, limit);
        List<Article> allArticle = articleService.getAllArticle(title, abstractText, "user");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", allArticle);
        PageInfo<Article> pageInfo = new PageInfo<>(allArticle);
        jsonObject.put("count", pageInfo.getTotal());

        return jsonObject;
    }

    /**
     * 获取文章列表页面数据 卡片组件
     *
     * @return 用户发布的所有文章list集合数据
     */
    @ResponseBody
    @GetMapping("/articleListCard")
    public JSONObject articleListCard(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer limit,
                                      String title, String abstractText){
        PageHelper.startPage(page, limit);

        JSONObject jsonObject = new JSONObject();
        List<Article> allArticle = articleService.getAllArticle(title, abstractText, "user");
        PageInfo<Article> pageInfo = new PageInfo<>(allArticle);
        jsonObject.put("count", pageInfo.getTotal());
        jsonObject.put("msg", "not data");
        jsonObject.put("code", 0);
        // 所有的文章 结果处理
        JSONArray articleArray = new JSONArray();
        if(CollectionUtil.isNotEmpty(allArticle)){
            for (Article article : allArticle) {
                JSONObject articleJson = new JSONObject();
                articleJson.put("id", article.getId());
                articleJson.put("image", "/blogs/images/article.jpg");
                articleJson.put("title", article.getTitle());
                articleJson.put("remark", article.getAbstractText());
                articleJson.put("time", article.getAddTimeShow());
                articleArray.add(articleJson);
            }
        }
        jsonObject.put("data", articleArray);

        return jsonObject;
    }

    /**
     * 跳转到展示文章详细内容的页面
     *
     * @param id 文章id
     * @param password 访问密码
     * @return 文章公开的情况下，页面 blogs/article/articleShow。
     *         文章不公开的情况下，验证当前用户与文章作者是否匹配。
     *                          匹配页面 blogs/article/articleShow
     *                          不匹配页面 blogs/user/login
     */
    @GetMapping("/articleShow")
    public String articleShow(Long id, String password, Model model, HttpSession session){
        Article article = articleService.getById(id);
        if (ObjectUtil.isNull(article)) {
            return "blogs/user/login";
        }
        // 查询文章用户的启用状态
        User user = userService.getUserById(article.getUserId());
        if (ObjectUtil.isNotNull(user)) {
            if (Boolean.valueOf(false).equals(user.getEnable())) {
                return "error/articleEnable";
            }
        }
        // 访问密码处理
        if (StrUtil.isNotBlank(article.getPassword())) {
            model.addAttribute("id", id);
            model.addAttribute("title", article.getTitle());
            if(StrUtil.isBlank(password)){
                return "blogs/article/articlePassword";
            }else {
                // 密码输入错误
                if(!password.equals(article.getPassword())){
                    model.addAttribute("info", "文章访问密码输入错误！！！");
                    return "blogs/article/articlePassword";
                }
            }
        }
        // 判断文章是否具有访问权限
        if (articleService.judgeShow(session, article)) {
            model.addAttribute("article", articleService.getArticleHeat(article));
            model.addAttribute("password", password);
            model.addAttribute("commentList", articleCommentService.getCommentByArticleId(id));
            return "blogs/article/articleShow";
        }
        return "blogs/user/login";
    }

    /**
     * 跳转到文章编辑页面
     *
     * @return 文章编辑页面 更新文章有权限操作
     *         用户登录页面 更新文章无权限操作
     */
    @GetMapping("/updateArticlePage")
    public String updateArticlePage(Model model, Long id){
        if (ObjectUtil.isNull(id)) {
            return "blogs/user/login";
        }
        // 查询要更新的文章 判断是否有操作权限
        Article article = articleService.articlePermission(id);
        model.addAttribute("article", article);
        // 获取用户的所有标签内容
        List<String> labelContent = labelService.getAllLabelContentByUser();
        model.addAttribute("labelList", labelContent);
        return "blogs/article/updateArticle";
    }

    /**
     * 场景：用户更新文章，保存到数据库。
     *
     * @param articleDTO 文章传输DTO对象
     * @return 数据 200代表成功 其他代表失败
     */
    @ResponseBody
    @PostMapping("/updateArticle")
    public ResultResponse updateArticle(@RequestBody @Validated ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);

        return ResultResponse.success(ArticleReturnCode.ARTICLE_UPDATE_OK_00003);
    }

    /**
     * 场景：用户逻辑删除文章，保存到数据库。
     *
     * @param id 文章id
     * @return 数据 200代表成功 其他代表失败
     */
    @ResponseBody
    @DeleteMapping("/deleteArticle")
    public ResultResponse deleteArticle(Long id){
        articleService.deleteArticle(id);

        return ResultResponse.success(ArticleReturnCode.ARTICLE_DELETE_OK_00005);
    }

    /**
     * 跳转到文章搜索页面
     *
     * @return 文章搜索页面
     */
    @GetMapping("/articleSearchPage")
    public String articleSearchPage(Model model, @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size, String searchArticleStr) {
        PageInfo<Article> pageInfo = articleService.search(page, size, searchArticleStr, null);
        model.addAttribute("list", articleService.getArticleHeat(pageInfo.getList()));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());
        model.addAttribute("search", searchArticleStr);
        return "blogs/article/articleSearch";
    }

    /**
     * 跳转到 文章 搜索页面 用于ES搜索引擎
     * 如果没有es搜索引擎组件 直接注释掉即可
     * 还需要用到mq组件 所以需要安装 elasticSearch 和 rabbitMQ
     *
     * @return
     */
    @GetMapping("/articleSearchESPage")
    public String articleSearchESPage(Model model,
                                    @RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    String searchArticleStr) throws IOException {
        HashMap<String, Object> hashMap = articleService.searchES(page, size, searchArticleStr);

        List<Article> list = (List<Article>) hashMap.get("list");
        Long pages = (Long) hashMap.get("pages");
        model.addAttribute("list", articleService.getArticleHeat(list));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pages);
        model.addAttribute("search", searchArticleStr);
        return "blogs/article/articleSearchES";
    }


    /**
     * 跳转到文章搜索页面 新版
     *
     * @return 文章搜索页面 新版
     */
    @GetMapping("/articleSearchPageNew")
    public String articleSearchPageNew(Model model, @RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size, String searchArticleStr, Long labelId){
        PageInfo<Article> pageInfo = articleService.search(page, size, searchArticleStr, labelId);
        model.addAttribute("list", articleCommentService.getArticleHeat(articleService.getArticleHeat(pageInfo.getList())));
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("pages", pageInfo.getPages());
        model.addAttribute("search", searchArticleStr);
        model.addAttribute("count", pageInfo.getTotal());
        // 获取公开文章的标签
        Set<Label> labelPublicShow = labelService.getLabelPublicShow();
        model.addAttribute("labelPublicShow", labelPublicShow);
        if (ObjectUtil.isNull(labelId)) {
            model.addAttribute("labelId", 0);
        } else {
            model.addAttribute("labelId", labelId);
        }
        // 获取热点前十文章
        Set<Article> articleHeatTop = articleService.getArticleHeatTop();
        model.addAttribute("articleHeatTop", articleHeatTop);
        return "blogs-new/article";
    }

    /**
     * 跳转到展示文章详细内容的页面 新版
     *
     * @param id 文章id
     * @param password 访问密码
     * @return 文章公开的情况下，页面 blogs-new/read。
     *         文章不公开的情况下，验证当前用户与文章作者是否匹配。
     *                          匹配页面 blogs-new/read
     *                          不匹配页面 blogs/user/login
     */
    @GetMapping("/articleShowNew")
    public String articleShowNew(Long id, String password, Model model, HttpSession session){
        Article article = articleService.getById(id);
        if(ObjectUtil.isNull(article)){
            return "blogs/user/login";
        }

        // 查询文章用户的启用状态
        User user = userService.getUserById(article.getUserId());
        if(ObjectUtil.isNotNull(user)){
            if(Boolean.valueOf(false).equals(user.getEnable())){
                return "error/articleEnable";
            }
        }

        // 访问密码处理
        if(StrUtil.isNotBlank(article.getPassword())){
            model.addAttribute("id", id);
            model.addAttribute("title", article.getTitle());
            if(StrUtil.isBlank(password)){
                return "blogs/article/articlePassword";
            }else {
                // 密码输入错误
                if(!password.equals(article.getPassword())){
                    model.addAttribute("info", "文章访问密码输入错误！！！");
                    return "blogs/article/articlePassword";
                }
            }
        }

        // 判断文章是否具有访问权限
        if(articleService.judgeShow(session, article)){
            model.addAttribute("article", articleService.getArticleHeat(article));
            model.addAttribute("password", password);
            model.addAttribute("commentList", articleCommentService.getCommentByArticleId(id));

            // 获取热点前十文章
            Set<Article> articleHeatTop = articleService.getArticleHeatTop();
            model.addAttribute("articleHeatTop", articleHeatTop);
            return "blogs-new/read";
        }

        return "blogs/user/login";
    }

    /**
     * 文章进行评论
     *
     * @param articleId 文章id
     * @param content   评论内容
     * @return 文章进行评论结果
     */
    @ResponseBody
    @PostMapping("/submitComment")
    public ResultResponse submitComment(Long articleId, String content,
                                        HttpServletRequest httpRequest, HttpSession session){
        registerService.remember(httpRequest, session);
        articleService.submitComment(articleId, content, session);

        return ResultResponse.success(ArticleReturnCode.ARTICLE_SUBMIT_COMMENT_OK_00012);
    }
}
