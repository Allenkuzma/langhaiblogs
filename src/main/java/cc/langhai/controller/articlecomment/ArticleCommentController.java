package cc.langhai.controller.articlecomment;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.ArticleComment;
import cc.langhai.response.ArticleCommentReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.IArticleCommentService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章评论前端控制器
 *
 * @author langhai
 * @date 2023-05-22 10:22
 */
@Controller
@RequestMapping("/articleComment")
public class ArticleCommentController {

    @Autowired
    private IArticleCommentService articleCommentService;

    /**
     * 跳转到文章评论管理页面（admin权限）
     *
     * @return 返回文章评论管理页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/articleCommentListPage")
    public String articleCommentListPage(){
        return "blogs/articleComment/articleCommentList";
    }

    /**
     * 获取用户文章评论列表页面数据
     * 场景：管理员管理用户的所有文章评论
     *
     * @return 文章评论列表
     */
    @ResponseBody
    @GetMapping("/articleComment")
    @RequestAuthority(value = {"admin"})
    public JSONObject articleComment(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  String content){
        // 开启分页助手
        PageHelper.startPage(page, limit);
        List<ArticleComment> allArticle = articleCommentService.getAllArticleComment(content);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", allArticle);
        PageInfo<ArticleComment> pageInfo = new PageInfo<>(allArticle);
        jsonObject.put("count", pageInfo.getTotal());
        return jsonObject;
    }

    /**
     * 场景：管理员永久真实删除文章评论，保存到数据库。
     *
     * @param id 文章评论id
     * @return 数据 200代表成功 其他代表失败
     */
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    @DeleteMapping("/deleteArticleComment")
    public ResultResponse deleteArticleComment(Long id){
        articleCommentService.deleteArticleComment(id);
        return ResultResponse.success(ArticleCommentReturnCode.ARTICLE_COMMENT_DELETE_OK_00001);
    }

}
