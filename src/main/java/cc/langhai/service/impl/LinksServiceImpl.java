package cc.langhai.service.impl;

import cc.langhai.config.constant.LinksConstant;
import cc.langhai.domain.Links;
import cc.langhai.mapper.LinksMapper;
import cc.langhai.service.LinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Override
    public List<Links> getSortAllLinks() {
        List<Links> linksList = this.list();
        Collections.sort(linksList);
        int count = linksList.size();
        if(linksList.size() < LinksConstant.LINKS_COUNT_ALL){
            for (int i = 0; i < LinksConstant.LINKS_COUNT_ALL - count; i++) {
                Links links = new Links();
                links.setTitle("请在系统中填充满六个友情链接");
                links.setDescription("请在系统中填充满六个友情链接");
                linksList.add(links);
            }
        }
        return linksList;
    }

}
