package cc.langhai.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    /**
     * 导航分类id
     *
     */
    private Long navClassifyId;

    /**
     * 导航网站名字
     *
     */
    private String name;

    private String imageUrl;

    /**
     * 导航网站图片路径
     *
     */
    private String url;

    /**
     * 导航网站描述
     *
     */
    private String description;

    /**
     * 添加时间
     *
     */
    private Date addTime;

    /**
     * 修改时间
     *
     */
    private Date updateTime;


}
