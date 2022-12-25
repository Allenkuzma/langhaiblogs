package cc.langhai.mapper;

import cc.langhai.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章表 Mapper
 *
 * @author langhai
 * @date 2022-12-24 16:35
 */
@Mapper
public interface ArticleMapper {

    /**
     * 获取用户某天发表文章数量
     *
     * @return
     */
    Integer getDayCount(Long userId, String beginDate, String endDate);

    /**
     * 新增文章
     *
     */
    void insertArticle(Article article);

    /**
     * 获取用户发布的所有文章
     *
     * @return
     */
    List<Article> getAllArticle(Long userId);

    /**
     * 获取一篇文章
     *
     * @param id
     * @return
     */
    Article getById(Long id);

}
