package cc.langhai.service.impl;

import cc.langhai.domain.DevLog;
import cc.langhai.mapper.DevLogMapper;
import cc.langhai.service.DevLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 开发日志记录 service 实现类
 *
 * @author langhai
 * @date 2023-01-16 19:14
 */
@Service
public class DevServiceImpl extends ServiceImpl<DevLogMapper, DevLog> implements DevLogService {

}
