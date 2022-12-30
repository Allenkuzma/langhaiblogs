package cc.langhai.controller.article;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.domain.User;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
import cc.langhai.service.LabelService;
import cc.langhai.service.UserService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    /**
     * 跳转到 文章 新发布页面
     *
     * @return
     */
    @GetMapping("/newArticlePage")
    public String newArticlePage(HttpSession session, Model model){
        List<Label> labelList = labelService.getAllLabelByUser();

        // arrayList 用来存储文章标签的内容
        List<String> collect = labelList.stream().map(label -> label.getContent()).collect(Collectors.toList());
        model.addAttribute("labelList", collect);

        return "blogs/article/newArticle";
    }

    /**
     * 场景：用户发布文章，保存到数据库。
     *
     * @param content          新增文章标签
     * @param label            使用存在的文章标签
     * @param publicShow       文章是否公开： 公开/不公开
     * @param title            文章标题
     * @param html             文章内容html形式
     * @return 数据 200代表成功 其他代表失败
     */
    @PostMapping("/issue")
    @ResponseBody
    public ResultResponse issue(String title,
                                String content,
                                String publicShow,
                                String html,
                                String label){
        articleService.issue(title, content, publicShow, html, label);
        return ResultResponse.success(ArticleReturnCode.ARTICLE_ISSUE_OK_00000);
    }

    /**
     * 跳转到 文章 列表页面
     *
     * @return
     */
    @GetMapping("/articleListPage")
    public String articleListPage(HttpSession session, Model model,
                                  @RequestParam(defaultValue = "1") Integer curr,
                                  @RequestParam(defaultValue = "10") Integer limitArticle){
        // 开启分页助手
        PageHelper.startPage(curr, limitArticle);

        List<Article> allArticle = articleService.getAllArticle();
        List<Article> articleHeat = articleService.getArticleHeat(allArticle);


        model.addAttribute("allArticle", articleHeat);
        model.addAttribute("curr", curr);
        return "blogs/article/articleList";
    }

    /**
     * 获取 文章 列表页面数据
     *
     * @return
     */
    @GetMapping("/articleList")
    @ResponseBody
    public JSONObject articleList(HttpSession session, Model model,
                                  @RequestParam(defaultValue = "1") Integer curr,
                                  @RequestParam(defaultValue = "10") Integer limitArticle){
        JSONObject jsonObject = new JSONObject();

        PageHelper.startPage(curr, limitArticle);

        List<Article> allArticle = articleService.getAllArticle();

        jsonObject.put("code", 0);

        jsonObject.put("data", allArticle);

        PageInfo<Article> pageInfo = new PageInfo<>(allArticle);

        jsonObject.put("count", pageInfo.getTotal());
        return jsonObject;
    }


    /**
     * 跳转到展示文章详细内容的页面
     *
     * @param   id 文章id
     * @return 文章公开的情况下，页面 langhaibk/article/article-show。
     *         文章不公开的情况下，验证当前用户与文章作者是否匹配。
     *                          匹配 页面 langhaibk/article/article-show
     *                          不匹配 页面 langhaibk/index
     */
    @GetMapping("/articleShow")
    public String articleShow(Long id, Model model, HttpSession session){
        Article article = articleService.getById(id);
        if(ObjectUtil.isNull(article)){
            return "blogs/user/login";
        }
        Article articleHeat = articleService.getArticleHeat(article);

        boolean judgeShow = articleService.judgeShow(session, article);
        if(judgeShow){
            model.addAttribute("article", articleHeat);
            return "blogs/article/articleShow";
        }


        return "blogs/user/login";
    }

    /**
     * 跳转到文章编辑页面
     *
     * @return
     */
    @GetMapping("/updateArticlePage")
    public String updateArticlePage(HttpSession session, Model model, Long id){
        if(ObjectUtil.isNull(id)){
            return "blogs/user/login";
        }

        // 查询要更新的文章
        Article article = articleService.getById(id);
        model.addAttribute("article", article);

        List<Label> labelList = labelService.getAllLabelByUser();
        // arrayList 用来存储文章标签的内容
        List<String> collect = labelList.stream().map(label -> label.getContent()).collect(Collectors.toList());
        model.addAttribute("labelList", collect);

        return "blogs/article/updateArticle";
    }

    /**
     * 场景：用户更新文章，保存到数据库。
     *
     * @param content          新增文章标签
     * @param label            使用存在的文章标签
     * @param publicShow       文章是否公开： 公开/不公开
     * @param title            文章标题
     * @param html             文章内容html形式
     * @return 数据 200代表成功 其他代表失败
     */
    @PostMapping("/updateArticle")
    @ResponseBody
    public ResultResponse updateArticle(String title,
                                String content,
                                String publicShow,
                                String html,
                                String label, Long id){
        articleService.updateArticle(title, content, publicShow, html, label, id);
        return ResultResponse.success(ArticleReturnCode.ARTICLE_UPDATE_OK_00003);
    }

    /**
     * 场景：用户逻辑删除文章，保存到数据库。
     *
     * @param id
     * @return 数据 200代表成功 其他代表失败
     */
    @PostMapping("/deleteArticle")
    @ResponseBody
    public ResultResponse deleteArticle(Long id){
        articleService.deleteArticle(id);
        return ResultResponse.success(ArticleReturnCode.ARTICLE_DELETE_OK_00005);
    }
}
