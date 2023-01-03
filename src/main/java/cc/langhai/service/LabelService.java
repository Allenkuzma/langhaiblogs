package cc.langhai.service;

import cc.langhai.domain.Label;

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
     * 新增标签的时候 进行校验
     *
     * @param content
     */
    Label verifyAddLabel(String content);
}
