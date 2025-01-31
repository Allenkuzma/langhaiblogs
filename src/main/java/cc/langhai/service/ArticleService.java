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
     * @param articleDTO 文章实体类 传输对象DTO
     * @return 文章id
     */
    Long issue(ArticleDTO articleDTO);

    /**
     * 获取用户发布的所有文章
     *
     * @param title 文章标题
     * @param abstractText 文章摘要
     * @param param 此参数传入为 system 查询所有用户文章数据
     * @return 用户发布的所有文章list集合数据
     */
    List<Article> getAllArticle(String title, String abstractText, String param);

    /**
     * 获取一篇文章
     *
     * @param id 文章id
     * @return 文章实体类
     */
    Article getById(Long id);

    /**
     * 获取文章热度
     * 使用场景：个人空间文章列表的时候使用
     *
     * @param articleList 文章集合
     * @return 文章集合
     */
    List<Article> getArticleHeat(List<Article> articleList);

    /**
     * 获取文章热度并且增加一个热度
     * 使用场景：某一篇文章展示的时候使用
     *
     * @param article 文章
     * @return 文章热度
     */
    Article getArticleHeat(Article article);

    /**
     * 判断文章是否具有访问权限
     *
     * @param session 会话
     * @param article 文章
     * @return 访问权限
     */
    boolean judgeShow(HttpSession session, Article article);

    /**
     * 用户更新文章，保存到数据库。
     *
     * @param articleDTO 文章传输DTO对象
     */
    void updateArticle(ArticleDTO articleDTO);

    /**
     * 用户逻辑删除文章，保存到数据库。
     *
     * @param id 文章id
     */
    void deleteArticle(Long id);

    /**
     * 文章搜索功能
     *
     * @param size              显示的条数
     * @param page              页数
     * @param searchArticleStr  搜索的文章标题
     * @param labelId           标签id
     * @return                  文章分页对象
     */
    PageInfo<Article> search(Integer page, Integer size, String searchArticleStr, Long labelId);

    /**
     * 文章搜索功能 用于ES搜索引擎
     *
     * @param size 显示的条数
     * @param page 页数
     * @param searchArticleStr 搜索词条
     * @return 搜索结果
     */
    HashMap<String, Object> searchES(Integer page, Integer size, String searchArticleStr) throws IOException;

    /**
     * 判断文章是否有权限操作
     *
     * @param id 文章实体类id
     * @return 文章实体类
     */
    Article articlePermission(Long id);

    /**
     * 获取热点前十的文章
     *
     * @return
     */
    Set<Article> getArticleHeatTop();

    /**
     * 管理员永久真实删除文章，保存到数据库。
     *
     * @param id 文章id
     */
    void systemDeleteArticle(Long id);

    /**
     * 文章进行评论
     *
     * @param articleId 文章id
     * @param content   评论内容
     * @param session   session会话
     */
    void submitComment(Long articleId, String content, HttpSession session);

    /**
     * 获取置顶文章
     *
     * @param page 页数
     * @param searchArticleStr 搜索内容
     * @param labelId 标签id
     * @return 置顶文章
     */
    List<Article> topArticle(Integer page, String searchArticleStr, Long labelId);

    /**
     * 修改文章置顶状态
     *
     * @param id 文章id
     * @param topFlag 置顶状态
     */
    void top(Long id, Boolean topFlag);

    /**
     * 修改文章审核状态
     *
     * @param id 文章id
     * @param checkFlag 审核状态
     */
    void check(Long id, Boolean checkFlag);

}
