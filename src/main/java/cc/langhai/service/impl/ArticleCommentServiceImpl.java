package cc.langhai.service.impl;

import cc.langhai.domain.Article;
import cc.langhai.domain.ArticleComment;
import cc.langhai.mapper.ArticleCommentMapper;
import cc.langhai.service.IArticleCommentService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章评论服务实现类 service接口 实现类
 *
 * @author langhai
 * @date 2023-05-22 10:31
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements IArticleCommentService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Override
    public List<ArticleComment> getCommentByArticleId(Long articleId) {
        List<ArticleComment> result = articleCommentMapper.getCommentByArticleId(articleId);

        return result;
    }

    @Override
    public List<Article> getArticleHeat(List<Article> articleList) {
        if(CollectionUtil.isNotEmpty(articleList)){
            for (Article article : articleList) {
                Integer count = articleCommentMapper.selectCount(Wrappers.<ArticleComment>lambdaQuery()
                        .eq(ArticleComment::getArticleId, article.getId()));
                article.setCommentCount(count);
            }
        }

        return articleList;
    }
}
