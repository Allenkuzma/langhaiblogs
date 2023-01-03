package cc.langhai.service;

import cc.langhai.domain.Image;

/**
 * 图片 service接口
 *
 * @author langhai
 * @date 2022-01-03 11:28
 */
public interface ImageService {

    /**
     * 判断用户存储的图片总大小
     *
     */
    void size();

    /**
     * 保存图片
     *
     * @param image
     */
    void saveImage(Image image);
}
