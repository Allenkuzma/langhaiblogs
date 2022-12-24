package cc.langhai.mapper;

import cc.langhai.domain.Article;
import org.apache.ibatis.annotations.Mapper;

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
}
