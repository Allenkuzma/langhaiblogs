package cc.langhai.service.impl;

import cc.langhai.domain.DevLog;
import cc.langhai.mapper.DevLogMapper;
import cc.langhai.service.DevLogService;
import cc.langhai.utils.UserContext;
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

    @Override
    public void addDevLog(DevLog devLog) {
        devLog.setUserId(UserContext.getUserId());
        this.save(devLog);
    }

    @Override
    public void updateDevLog(DevLog devLog) {
        devLog.setUserId(UserContext.getUserId());
        this.updateById(devLog);
    }

    @Override
    public void deleteDevLog(Long id) {
        this.removeById(id);
    }
}
