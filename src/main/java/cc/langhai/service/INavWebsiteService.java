package cc.langhai.service;

import cc.langhai.domain.NavWebsite;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 导航网站 service接口
 *
 * @author langhai
 * @date 2022-01-27 17:22
 */
public interface INavWebsiteService extends IService<NavWebsite> {

    /**
     * 新增公共导航网站
     *
     * @param navWebsite 公共导航网站实体类
     */
    void addNavWebsite(NavWebsite navWebsite);

    /**
     * 更新公共导航网站
     *
     * @param navWebsite 公共导航网站实体类
     */
    void updateNavWebsite(NavWebsite navWebsite);

    /**
     * 删除公共导航网站
     *
     * @param id 公共导航网站实体id
     */
    void deleteNavWebsite(Long id);
}
