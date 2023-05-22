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
}
