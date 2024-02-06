package cc.langhai.domain;

import cc.langhai.exception.BusinessException;
import cc.langhai.response.ArticleReturnCode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 文章实体类
 *
 * @author langhai
 * @date 2022-12-24 16:30
 */
@Data
public class Article implements Comparable {

    private Long id;

    private Long userId;

    private Long labelId;

    @NotBlank(message = "文章标题不能为空")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    private String html;

    private String abstractText;

    private String password;

    private Integer publicShow;

    private Integer deleteFlag;

    private Date addTime;

    private Date updateTime;

    /**
     * 文章作者
     *
     */
    private String author;

    /**
     * 用来展示新增时间 yyyy-MM-dd HH:mm:ss
     *
     */
    private String addTimeShow;

    private String updateTimeShow;

    private String year;

    private String month;

    private String day;

    /**
     * 文章热度
     */
    private String heat;

    /**
     * 文章标签
     */
    private String labelContent;

    /**
     * 文章评论次数
     */
    private Integer commentCount;

    @Override
    public int compareTo(Object o) {
        if(o instanceof Article){
            Article article = (Article) o;
            if(Long.compare(Long.valueOf(article.getHeat()), Long.valueOf(this.getHeat())) == 0){
                return Long.compare(article.getId(), this.getId());
            }else {
                return Long.compare(Long.valueOf(article.getHeat()), Long.valueOf(this.getHeat()));
            }
        }else {
            throw new BusinessException(ArticleReturnCode.ARTICLE_SORT_FAIL_00008);
        }
    }
}
