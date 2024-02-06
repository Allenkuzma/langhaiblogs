package cc.langhai.service.impl;

import cc.langhai.domain.Visit;
import cc.langhai.mapper.VisitMapper;
import cc.langhai.service.VisitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户访问实体表 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-12 11:14
 */
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {
}
