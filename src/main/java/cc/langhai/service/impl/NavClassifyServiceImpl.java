package cc.langhai.service.impl;

import cc.langhai.domain.NavClassify;
import cc.langhai.mapper.NavClassifyMapper;
import cc.langhai.service.INavClassifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导航分类 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-27 16:54
 */
@Service
public class NavClassifyServiceImpl extends ServiceImpl<NavClassifyMapper, NavClassify> implements INavClassifyService {

    @Autowired
    private NavClassifyMapper navClassifyMapper;

    @Override
    public List<NavClassify> getPublicNav() {
        List<NavClassify> list = navClassifyMapper.getPublicNav();
        return list;
    }
}
