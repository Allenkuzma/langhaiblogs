package cc.langhai.mapper;

import cc.langhai.domain.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章评论Mapper 接口
 *
 * @author langhai
 * @date 2023-05-22 10:14
 */
@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    /**
     * 根据文章id获取所有评论
     *
     * @param articleId 文章id
     * @return 文章评论集合
     */
    List<ArticleComment> getCommentByArticleId(Long articleId);
}
