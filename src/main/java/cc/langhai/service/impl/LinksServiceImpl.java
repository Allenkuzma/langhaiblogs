package cc.langhai.service.impl;

import cc.langhai.config.constant.LinksConstant;
import cc.langhai.domain.Links;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.LinksMapper;
import cc.langhai.response.LinksReturnCode;
import cc.langhai.service.LinksService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 首页超链接管理 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-12 13:10
 */
@Service
public class LinksServiceImpl extends ServiceImpl<LinksMapper, Links> implements LinksService {

    @Override
    public void addLinks(Links links) {

        this.save(links);
    }

    @Override
    public void deleteLinks(Long id) {

        this.removeById(id);
    }

    @Override
    public void updateLinks(Links links) {

        this.updateById(links);
    }

}
