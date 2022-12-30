package cc.langhai.service;

import cc.langhai.domain.Article;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * article service接口
 *
 * @author langhai
 * @date 2022-12-24 16:09
 */
public interface ArticleService {

    /**
     * 用户发布文章，保存到数据库。
     *
     * @param title
     * @param content
     * @param publicShow
     * @param html
     * @param label
     */
    void issue(String title, String content, String publicShow, String html, String label);

    /**
     * 获取用户发布的所有文章
     *
     * @return
     */
    List<Article> getAllArticle();

    /**
     * 获取一篇文章
     *
     * @param id
     * @return
     */
    Article getById(Long id);

    /**
     * 获取文章热度
     * 使用场景：个人空间文章列表的时候使用
     *
     * @param articleList
     * @return
     */
    List<Article> getArticleHeat(List<Article> articleList);

    /**
     * 获取文章热度并且增加一个热度
     * 使用场景：某一篇文章展示的时候使用
     *
     * @param article
     * @return
     */
    Article getArticleHeat(Article article);

    /**
     * 判断文章是否具有访问权限
     *
     * @param session
     * @param article
     * @return
     */
    boolean judgeShow(HttpSession session, Article article);

    /**
     * 用户更新文章，保存到数据库。
     *
     * @param title
     * @param content
     * @param publicShow
     * @param html
     * @param label
     * @param id
     */
    void updateArticle(String title, String content, String publicShow, String html, String label, Long id);

    /**
     * 用户逻辑删除文章，保存到数据库。
     *
     * @param id
     */
    void deleteArticle(Long id);
}
