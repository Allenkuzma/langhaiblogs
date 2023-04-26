package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * minio图片存储实体类
 *
 * @author langhai
 * @date 2023-01-03 11:19
 */
@Data
public class Image {

    private Long id;

    private String fileName;

    private String minioName;

    private Long fileSize;

    private Long userId;

    private Date addTime;

    /**
     * 前端展示图片地址
     */
    @TableField(exist = false)
    private String url;

    /**
     * 用来展示新增时间 yyyy-MM-dd HH:mm:ss
     */
    @TableField(exist = false)
    private String addTimeShow;


}
