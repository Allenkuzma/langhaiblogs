package cc.langhai.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 用来展示新增时间 yyyy-MM-dd HH:mm:ss
     *
     */
    private String addTimeShow;

    /**
     * 用来展示修改时间 yyyy-MM-dd HH:mm:ss
     *
     */
    private String updateTimeShow;
}
