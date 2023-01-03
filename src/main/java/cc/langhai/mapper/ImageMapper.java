package cc.langhai.mapper;

import cc.langhai.domain.Article;
import cc.langhai.domain.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 图片表 Mapper
 *
 * @author langhai
 * @date 2022-01-03 11:31
 */
@Mapper
public interface ImageMapper {

    /**
     * 获取用户的所有图片
     *
     * @param userId
     * @return
     */
    List<Image> getAllImageByUser(Long userId);

    /**
     * 新增图片
     *
     * @param image
     */
    void insertImage(Image image);
}
