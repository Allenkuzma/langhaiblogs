package cc.langhai.domain;

import lombok.Data;

import java.util.Date;

/**
 * 文章标签 实体类
 *
 * @author langhai
 * @date 2022-12-24 10:45
 */
@Data
public class Label {

    private Long id;

    private Long userId;

    private String content;

    private Date addTime;

    private Date updateTime;
}
