package cc.langhai.config.constant;

import lombok.Data;

/**
 * 图片常量类
 *
 * @author langhai
 * @date 2023-01-03 11:36
 */
@Data
public class ImageConstant {

    /**
     * 用户图片存储总数量字节 默认设置为50M
     */
    public static final Long IMAGE_COUNT_ALL = 52428800L;

    /**
     * admin vip 用户图片存储总数量字节 默认设置为100M
     */
    public static final Long IMAGE_COUNT_ADMIN_VIP_ALL = 104857600L;

}
