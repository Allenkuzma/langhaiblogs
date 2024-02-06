package cc.langhai.service;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

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
     * @return 用户拥有的所有标签
     */
    List<Label> getAllLabelByUser();

    /**
     * 获取用户的所有标签内容
     *
     * @return 标签内容集合
     */
    List<String> getAllLabelContentByUser();

    /**
     * 新增标签的时候进行校验
     *
     * @param content 标签内容
     * @return 标签实体类
     */
    Label verifyAddLabel(String content);

    /**
     * 删除标签
     *
     * @param id 标签id
     */
    void deleteLabel(Long id);

    /**
     * 更新标签
     *
     * @param id 标签id
     * @param content 标签内容
     */
    void updateLabel(Long id, String content);

    /**
     * 用户该标签下所有的文章
     *
     * @param page 页数
     * @param size 每页显示条数
     * @param id 标签id
     * @return 文章PageInfo对象
     */
    PageInfo<Article> article(Integer page, Integer size, Long id);

    /**
     * id获取标签内容
     *
     * @param id
     * @return
     */
    Label getById(Long id);

    /**
     * 判断标签是否有权限操作
     *
     * @param id
     * @return
     */
    Label labelPermission(Long id);

    /**
     * 判断标签是否存在文章
     *
     * @param context 标签内容
     * @return 标签实体类
     */
    Label labelExist(String context);

    /**
     * 获取公开文章的所有标签
     *
     * @return 公开文章的所有标签
     */
    Set<Label> getLabelPublicShow();
}
