package cc.langhai.domain;

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


}
