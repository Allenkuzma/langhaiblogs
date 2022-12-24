package cc.langhai.controller.article;

import cc.langhai.domain.Label;
import cc.langhai.response.ArticleReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
import cc.langhai.service.LabelService;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    /**
     * 跳转到 文章 新发布页面
     *
     * @return
     */
    @GetMapping("/newArticlePage")
    public String newArticlePage(HttpSession session, Model model){
        List<Label> labelList = labelService.getAllLabelByUser();

        //arrayList 用来存储文章标签的内容
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
}
