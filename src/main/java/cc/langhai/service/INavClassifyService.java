package cc.langhai.service;

import cc.langhai.domain.NavClassify;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 导航分类 service接口
 *
 * @author langhai
 * @date 2022-01-27 16:54
 */
public interface INavClassifyService extends IService<NavClassify> {

    /**
     * 获取公共的导航分类以及站点信息
     *
     * @return
     */
    List<NavClassify> getPublicNav();

    /**
     * 新增公共导航分类
     *
     * @param navClassify
     */
    void addNavClassify(NavClassify navClassify);

    /**
     * 删除公共导航分类
     *
     * @param id
     */
    void deleteNavClassify(Long id);

    /**
     * 获取list集合icon名字
     *
     * @return
     */
    List<String> getIconList() throws IllegalAccessException;

    /**
     * 更新公共导航分类
     *
     * @param navClassify
     */
    void updateNav(NavClassify navClassify);
}
