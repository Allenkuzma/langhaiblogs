package cc.langhai.controller.article;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.Article;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
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
import java.util.List;

/**
 * 管理员系统文章控制器
 *
 * @author langhai
 * @date 2023-04-18 15:32
 */
@Controller
@RequestMapping("/system/article")
public class SystemArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到文章列表管理页面（admin权限）
     *
     * @return 返回文章列表管理页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/systemArticleListPage")
    public String systemArticleListPage(){

        return "blogs/article/systemArticleList";
    }

    /**
     * 获取用户文章列表页面数据
     * 场景：管理员管理用户的所有文章
     *
     * @return 文章列表
     */
    @ResponseBody
    @GetMapping("/systemArticleList")
    @RequestAuthority(value = {"admin"})
    public JSONObject articleList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  String title, String abstractText){
        // 开启分页助手
        PageHelper.startPage(page, limit);
        List<Article> allArticle = articleService.getAllArticle(title, abstractText, "system");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", allArticle);
        PageInfo<Article> pageInfo = new PageInfo<>(allArticle);
        jsonObject.put("count", pageInfo.getTotal());

        return jsonObject;
    }

    /**
     * 管理员跳转到展示文章详细内容的页面
     *
     * @param id 文章id
     * @return 管理员查看文章页面
     */
    @GetMapping("/systemArticleShow")
    @RequestAuthority(value = {"admin"})
    public String articleShow(Long id, Model model){
        Article article = articleService.getById(id);
        if(ObjectUtil.isNull(article)){
            return "blogs/user/login";
        }

        model.addAttribute("article", articleService.getArticleHeat(article));

        return "blogs/article/systemArticleShow";
    }

    /**
     * 场景：管理员永久真实删除文章，保存到数据库。
     *
     * @param id 文章id
     * @return 数据 200代表成功 其他代表失败
     */
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    @DeleteMapping("/systemDeleteArticle")
    public ResultResponse deleteArticle(Long id){
        articleService.systemDeleteArticle(id);

        return ResultResponse.success(ArticleReturnCode.ARTICLE_SYSTEM_DELETE_OK_00011);
    }
}
