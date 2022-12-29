package cc.langhai.domain;

import lombok.Data;

import java.util.Date;

/**
 * 文章实体类
 *
 * @author langhai
 * @date 2022-12-24 16:30
 */
@Data
public class Article {

    private Long id;

    private Long userId;

    private Long labelId;

    private String title;

    private String html;

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

    /**
     * 文章热度
     *
     */
    private String heat;

    /**
     * 文章标签
     *
     */
    private String labelContent;
}
