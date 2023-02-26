package cc.langhai.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 导航网站 实体类
 *
 * @author langhai
 * @date 2023-01-27 16:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("nav_website")
public class NavWebsite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 导航分类id
     *
     */
    @NotNull(message = "导航分类id不能为空")
    private Long navClassifyId;

    /**
     * 导航网站名字
     *
     */
    @NotBlank(message = "导航网站名字不能为空")
    private String name;

    /**
     * 导航网站logo图片地址
     *
     */
    @NotBlank(message = "导航网站logo图片地址不能为空")
    private String imageUrl;

    /**
     * 导航网站路径
     *
     */
    @NotBlank(message = "导航网站路径不能为空")
    private String url;

    /**
     * 导航网站描述
     *
     */
    @NotBlank(message = "导航网站描述不能为空")
    private String description;

    /**
     * 添加时间
     *
     */
    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    /**
     * 修改时间
     *
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;


}
