package cc.langhai.mapper;

import cc.langhai.domain.NavClassify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 导航分类 Mapper
 *
 * @author langhai
 * @date 2022-01-07 16:56
 */
public interface NavClassifyMapper extends BaseMapper<NavClassify> {

    /**
     * 获取公共的导航分类以及站点信息
     *
     * @return
     */
    List<NavClassify> getPublicNav();

}
