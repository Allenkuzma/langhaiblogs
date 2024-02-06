package cc.langhai.service;

import cc.langhai.domain.Links;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 首页超链接管理 service接口
 *
 * @author langhai
 * @date 2022-01-12 13:09
 */
public interface LinksService extends IService<Links> {

    /**
     * 管理员添加友情链接
     *
     * @param links 友情链接实体类
     */
    void addLinks(Links links);

    /**
     * 管理员删除友情链接
     *
     * @param id
     */
    void deleteLinks(Long id);

    /**
     * 管理员更新友情链接
     *
     * @param links 友情链接实体类
     */
    void updateLinks(Links links);

    /**
     * 获取所有的排序友情链接
     *
     * @return 所有的排序友情链接集合
     */
    List<Links> getSortAllLinks();
}
