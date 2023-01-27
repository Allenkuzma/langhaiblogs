package cc.langhai.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String name;

    /**
     * 导航分类图标
     *
     */
    private String icon;

    /**
     * 导航分类标签名字
     *
     */
    private String tagName;

    /**
     * 新增时间
     *
     */
    private Date addTime;

    /**
     * 更新时间
     *
     */
    private Date updateTime;

    /**
     * 该分类下的网站列表
     *
     */
    @TableField(exist = false)
    private List<NavWebsite> websiteList;


}
