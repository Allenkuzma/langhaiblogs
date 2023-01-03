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
     * TODO 后期优化
     *
     */
    public static final Long IMAGE_COUNT_ALL = 52428800L;

}
