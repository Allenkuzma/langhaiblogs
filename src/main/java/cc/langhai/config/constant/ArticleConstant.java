package cc.langhai.config.constant;

import cc.langhai.domain.Article;
import lombok.Data;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 文章常量类
 *
 * @author langhai
 * @date 2022-12-24 16:25
 */
@Data
public class ArticleConstant {

    /**
     * 文章当天发布次数
     *
     */
    public static final Integer ARTICLE_COUNT_TODAY = 10;

    /**
     * 热点排名前十的文章
     *
     */
    public static TreeSet<Article> ARTICLE_HEAT_TOP_10 = new TreeSet<>();

}
