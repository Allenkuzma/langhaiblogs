package cc.langhai.mapper;

import cc.langhai.domain.Label;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章标签表 Mapper
 *
 * @author langhai
 * @date 2022-12-24 10:48
 */
@Mapper
public interface LabelMapper {

    /**
     * 获取用户的所有标签
     *
     * @return
     */
    List<Label> getAllLabelByUser(Long userId);

    /**
     * 新增标签
     *
     * @param label
     */
    void insertLabel(Label label);

    /**
     * 查询用户的标签
     *
     * @param userId
     * @param content
     * @return
     */
    Label getLabelByUserAndContent(Long userId, String content);

}
