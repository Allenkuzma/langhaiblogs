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
}
