package cc.langhai.service;

import cc.langhai.domain.DevLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 开发日志记录 service
 *
 * @author langhai
 * @date 2023-01-16 19:07
 */
public interface DevLogService extends IService<DevLog> {

    /**
     * 管理员新增开发日志记录
     *
     * @param devLog
     */
    void addDevLog(DevLog devLog);
}
