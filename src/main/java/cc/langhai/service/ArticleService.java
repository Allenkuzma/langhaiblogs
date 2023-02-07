package cc.langhai.service;

import cc.langhai.domain.Article;
import cc.langhai.dto.ArticleDTO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
     * @param articleDTO
     */
    void issue(ArticleDTO articleDTO);

    /**
     * 获取用户发布的所有文章
     *
     * @return
     */
    List<Article> getAllArticle();

    /**
     * 获取一篇文章
     *
     * @param id 文章id
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
     * @param articleDTO
     */
    void updateArticle(ArticleDTO articleDTO);

    /**
     * 用户逻辑删除文章，保存到数据库。
     *
     * @param id
     */
    void deleteArticle(Long id);

    /**
     * 文章搜索功能
     *
     * @param size
     * @param page
     * @param searchArticleStr
     * @param labelId
     * @return
     */
    PageInfo<Article> search(Integer page, Integer size, String searchArticleStr, Long labelId);

    /**
     * 文章搜索功能 用于ES搜索引擎
     *
     * @param size
     * @param page
     * @param searchArticleStr
     * @return
     */
    HashMap<String, Object> searchES(Integer page, Integer size, String searchArticleStr) throws IOException;

    /**
     * 判断文章是否有权限操作
     *
     * @param id 文章实体类id
     * @return
     */
    Article articlePermission(Long id);

    /**
     * 获取热点前十的文章
     *
     * @return
     */
    Set<Article> getArticleHeatTop();

}
