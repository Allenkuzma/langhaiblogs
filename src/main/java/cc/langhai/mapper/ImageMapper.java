package cc.langhai.mapper;

import cc.langhai.domain.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片表Mapper
 *
 * @author langhai
 * @date 2022-01-03 11:31
 */
@Mapper
public interface ImageMapper extends BaseMapper<Image> {

    /**
     * 获取用户的所有图片
     *
     * @param userId 用户id
     * @param searchImageStr minio文件名字
     * @return 用户所有图片集合
     */
    List<Image> getAllImageByUser(Long userId, String searchImageStr);

    /**
     * 新增图片
     *
     * @param image minio图片存储实体类
     */
    void insertImage(Image image);

    /**
     * 获取单张图片
     *
     * @param objectName 文件名称
     * @return 单张图片
     */
    Image getImageByMinioName(String objectName);

    /**
     * 删除图片
     *
     * @param objectName 文件名称
     */
    void deleteImage(String objectName);
}
