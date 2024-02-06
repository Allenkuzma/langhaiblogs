package cc.langhai.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 导航网站实体类
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
     * 导航网站首页截图
     *
     */
    @NotBlank(message = "导航网站首页截图不能为空")
    private String indexImageUrl;

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
     * 导航网站详情信息
     *
     */
    @NotBlank(message = "导航网站详情信息不能为空")
    private String details;

    /**
     * 导航网站评分
     *
     */
    @NotNull(message = "导航网站评分不能为空")
    @Range(min = 1, max = 100, message = "导航网站评分范围在1-100分。")
    private Integer score;

    /**
     * 添加时间
     *
     */
    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    /**
     * 修改时间
     *
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 导航网站id
     *
     */
    @TableField(exist = false)
    private Long websiteId;

    /**
     * 添加时间前端展示
     *
     */
    @TableField(exist = false)
    private String addTimeShow;
}
