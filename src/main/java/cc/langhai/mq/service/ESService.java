package cc.langhai.mq.service;

import cc.langhai.domain.Article;

import java.io.IOException;

/**
 * es搜索引擎 service接口
 *
 * @author langhai
 * @date 2023-01-09 10:38
 */
public interface ESService {

    /**
     * 删除索引库当中的文档
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 增加 / 更新 索引库当中的文档
     *
     * @param id
     */
    void insertById(Long id);

    /**
     * id 查询文档
     *
     * @param id
     * @return
     */
    Article getById(Long id) throws IOException;
}
