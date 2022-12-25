package cc.langhai.service;

import cc.langhai.domain.Article;

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
}
