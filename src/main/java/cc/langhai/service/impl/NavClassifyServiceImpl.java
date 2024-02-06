package cc.langhai.service.impl;

import cc.langhai.config.constant.IconfontConstant;
import cc.langhai.config.constant.NavClassifyConstant;
import cc.langhai.domain.NavClassify;
import cc.langhai.domain.NavWebsite;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.NavClassifyMapper;
import cc.langhai.response.NavClassifyReturnCode;
import cc.langhai.service.INavClassifyService;
import cc.langhai.service.INavWebsiteService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
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

    @Autowired
    private INavWebsiteService navWebsiteService;

    @Override
    public List<NavClassify> getPublicNav() {
        // 查询公共导航网站分类以及分类下的网站
        List<NavClassify> list = navClassifyMapper.getPublicNav();
        return list;
    }

    @Override
    public void addNavClassify(NavClassify navClassify) {
        // 判断公共导航分类数量上限
        List<NavClassify> list = this.list(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0));
        if(list.size() >= NavClassifyConstant.NAV_CLASSIFY_COUNT_ALL){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_COUNT_FAIL_00000);
        }

        // 判读分类名字以及分类标签名字是否有重复的
        NavClassify nameClassify = this.getOne(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0)
                .eq(NavClassify::getName, navClassify.getName()));

        if(ObjectUtil.isNotNull(nameClassify)){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_NAME_EXISTENCE_00003);
        }

        NavClassify tagNameClassify = this.getOne(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0)
                .eq(NavClassify::getTagName, navClassify.getTagName()));

        if(ObjectUtil.isNotNull(tagNameClassify)){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_TAG_NAME_EXISTENCE_00004);
        }

        // 插入数据库，进行添加。
        navClassify.setUserId(0L);
        this.save(navClassify);
    }

    @Override
    public void deleteNavClassify(Long id) {
        // 判断该导航分类下是否存在网站
        List<NavWebsite> list = navWebsiteService.list(Wrappers.<NavWebsite>lambdaQuery()
                .eq(NavWebsite::getNavClassifyId, id));
        if(CollUtil.isNotEmpty(list)){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_DELETE_FAIL_EXIST_WEBSITE_00007);
        }

        this.removeById(id);
    }

    @Override
    public List<String> getIconList() throws IllegalAccessException {
        ArrayList<String> iconfont = new ArrayList<>();
        // 遍历输出属性
        Field[] fields =  IconfontConstant.class.getDeclaredFields();
        for( int i = 0; i < fields.length; i++){
            Field f = fields[i];
            iconfont.add(f.get(IconfontConstant.class).toString());
        }

        return iconfont;
    }

    @Override
    public void updateNav(NavClassify navClassify) {
        // 判读分类名字以及分类标签名字是否有重复的
        NavClassify nameClassify = this.getOne(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0)
                .eq(NavClassify::getName, navClassify.getName())
                .ne(NavClassify::getId, navClassify.getId()));

        if(ObjectUtil.isNotNull(nameClassify)){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_NAME_EXISTENCE_00003);
        }

        NavClassify tagNameClassify = this.getOne(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0)
                .eq(NavClassify::getTagName, navClassify.getTagName())
                .ne(NavClassify::getId, navClassify.getId()));

        if(ObjectUtil.isNotNull(tagNameClassify)){
            throw new BusinessException(NavClassifyReturnCode.NAV_CLASSIFY_TAG_NAME_EXISTENCE_00004);
        }

        this.updateById(navClassify);
    }

}
