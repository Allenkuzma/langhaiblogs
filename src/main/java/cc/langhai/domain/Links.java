package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 首页超链接管理
 *
 * @author langhai
 * @date 2022-01-12 13:04
 */
@Data
@TableName("links")
public class Links {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("title")
    private String title;

    @TableField("href")
    private String href;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("add_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    @TableField("update_time")
    private Date updateTime;
}
