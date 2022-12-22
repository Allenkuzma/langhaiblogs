package cc.langhai.controller.article;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 文章控制器
 *
 * @author langhai
 * @date 2022-12-22 22:22
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    /**
     * 跳转到 文章 新发布页面
     *
     * @return
     */
    @GetMapping("/newArticlePage")
    public String newArticlePage(HttpSession session){


        return "blogs/article/newArticle";
    }
}
