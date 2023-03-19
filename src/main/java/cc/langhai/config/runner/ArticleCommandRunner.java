package cc.langhai.config.runner;

import cc.langhai.config.constant.ArticleConstant;
import cc.langhai.domain.Article;
import cc.langhai.service.ArticleService;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TreeSet;

/**
 * 热点排名前十的文章预热
 *
 * @author langhai
 * @date 2023-03-19 18:38
 */
@Slf4j
@Component
public class ArticleCommandRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        // 获取10篇公开文章
        PageInfo<Article> pageInfo = articleService.search(1, 10, null, null);
        List<Article> articleHeat = articleService.getArticleHeat(pageInfo.getList());
        TreeSet<Article> articleHeatTop10 = ArticleConstant.ARTICLE_HEAT_TOP_10;
        if(CollectionUtil.isNotEmpty(articleHeat)){
            articleHeatTop10.addAll(articleHeat);
            log.info("10篇文章成功预热");
        }

    }
}
