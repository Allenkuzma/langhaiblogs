package cc.langhai.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 导航分类 实体类
 *
 * @author langhai
 * @date 2023-01-27 16:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("nav_classify")
public class NavClassify implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     *
     */
    private Long userId;

    /**
     * 导航分类名字
     *
     */
    @NotBlank(message = "导航分类名字不能为空")
    private String name;

    /**
     * 导航分类图标
     *
     */
    @NotBlank(message = "导航分类图标不能为空")
    private String icon;

    /**
     * 导航分类标签名字
     *
     */
    @NotBlank(message = "导航分类标签名字不能为空")
    private String tagName;

    /**
     * 新增时间
     *
     */
    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    /**
     * 更新时间
     *
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    /**
     * 该分类下的网站列表
     *
     */
    @TableField(exist = false)
    private List<NavWebsite> websiteList;


}
