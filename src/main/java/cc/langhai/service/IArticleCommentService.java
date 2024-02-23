package cc.langhai.service;

import cc.langhai.domain.Article;
import cc.langhai.domain.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 文章评论服务类 service接口
 *
 * @author langhai
 * @date 2023-05-22 10:29
 */
public interface IArticleCommentService extends IService<ArticleComment> {

    /**
     * 根据文章id获取所有评论
     *
     * @param articleId 文章id
     * @return 文章评论集合
     */
    List<ArticleComment> getCommentByArticleId(Long articleId);

    /**
     * 获取文章评论次数
     * 使用场景：文章库文章列表的时候使用
     *
     * @param articleList 文章集合
     * @return 文章集合
     */
    List<Article> getArticleHeat(List<Article> articleList);

    /**
     * 获取所有的文章评论
     *
     * @param content 评论内容
     * @return 文章评论集合
     */
    List<ArticleComment> getAllArticleComment(String content);

    /**
     * 删除文章评论
     *
     * @param id 文章评论id
     */
    void deleteArticleComment(Long id);

    /**
     * 修改评论审核状态
     *
     * @param id 评论id
     * @param showFlag 启用状态
     */
    void show(Long id, Boolean showFlag);
}
