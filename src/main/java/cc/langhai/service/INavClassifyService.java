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
}
