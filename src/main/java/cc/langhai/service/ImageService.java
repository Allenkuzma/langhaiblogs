package cc.langhai.service;

import cc.langhai.domain.Image;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 图片service接口
 *
 * @author langhai
 * @date 2022-01-03 11:28
 */
public interface ImageService extends IService<Image> {

    /**
     * 判断用户存储的图片总大小
     *
     * @return 用户存储的图片总大小
     */
    Long size();

    /**
     * 保存图片
     *
     * @param image minio图片存储实体类
     */
    void saveImage(Image image);

    /**
     * 获取用户所有图片
     *
     * @param userId 用户id
     * @param searchImageStr minio文件名字
     * @return 用户所有图片集合
     */
    List<Image> getAllImageByUser(Long userId, String searchImageStr);

    /**
     * 返回用户使用空间
     *
     * @return 用户占用空间百分比
     */
    String space();

    /**
     * 判断是否有操作权限
     *
     * @param objectName 文件名称
     * @return 是否有操作权限结果
     */
    boolean power(String objectName);

    /**
     * 删除图片
     *
     * @param objectName 文件名称
     */
    void delete(String objectName);
}
