package cc.langhai.service.impl;

import cc.langhai.domain.Links;
import cc.langhai.mapper.LinksMapper;
import cc.langhai.service.LinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 首页超链接管理 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-12 13:10
 */
@Service
public class LinksServiceImpl extends ServiceImpl<LinksMapper, Links> implements LinksService {
}
