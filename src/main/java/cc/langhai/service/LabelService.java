package cc.langhai.service;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 文章标签表 service接口
 *
 * @author langhai
 * @date 2022-12-24 10:50
 */
public interface LabelService {


    /**
     * 获取用户的所有标签
     *
     * @return
     */
    List<Label> getAllLabelByUser();

    /**
     * 获取用户的所有标签内容
     *
     * @return
     */
    List<String> getAllLabelContentByUser();

    /**
     * 新增标签的时候 进行校验
     *
     * @param content
     */
    Label verifyAddLabel(String content);

    /**
     * 删除标签
     *
     * @param id
     */
    void deleteLabel(Long id);

    /**
     * 更新标签
     *
     * @param id
     * @param content
     */
    void updateLabel(Long id, String content);

    /**
     * 用户该标签下所有的文章
     *
     * @param page
     * @param size
     * @param id
     * @return
     */
    PageInfo<Article> article(Integer page, Integer size, Long id);

    /**
     * id获取标签内容
     *
     * @param id
     * @return
     */
    Label getById(Long id);
}
