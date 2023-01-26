package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "h3标签内容不能为空")
    private String title;

    @TableField("href")
    @NotBlank(message = "超链接地址不能为空")
    private String href;

    @TableField("name")
    @NotBlank(message = "网站名字不能为空")
    private String name;

    @TableField("description")
    @NotBlank(message = "网站描述不能为空")
    private String description;

    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
}
