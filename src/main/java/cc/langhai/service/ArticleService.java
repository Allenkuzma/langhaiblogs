package cc.langhai.service;

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
}
