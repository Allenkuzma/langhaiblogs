package cc.langhai.service.impl;

import cc.langhai.config.constant.NavWebsiteConstant;
import cc.langhai.domain.NavWebsite;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.NavWebsiteMapper;
import cc.langhai.response.NavWebsiteReturnCode;
import cc.langhai.service.INavWebsiteService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 导航网站 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-27 17:23
 */
@Service
public class NavWebsiteServiceImpl extends ServiceImpl<NavWebsiteMapper, NavWebsite> implements INavWebsiteService {

    @Override
    public void addNavWebsite(NavWebsite navWebsite) {
        // 判断每一个分类下网站的数量上限
        List<NavWebsite> websiteList = this.list(Wrappers.<NavWebsite>lambdaQuery()
                .eq(NavWebsite::getNavClassifyId, navWebsite.getNavClassifyId()));
        if(websiteList.size() >= NavWebsiteConstant.NAV_WEBSITE_CLASSIFY_COUNT_ALL){
            throw new BusinessException(NavWebsiteReturnCode.NAV_WEBSITE_CLASSIFY_COUNT_FAIL_00000);
        }

        // 插入数据库，进行添加。
        this.save(navWebsite);
    }

    @Override
    public void updateNavWebsite(NavWebsite navWebsite) {
        this.updateById(navWebsite);
    }

    @Override
    public void deleteNavWebsite(Long id) {
        this.removeById(id);
    }
}
