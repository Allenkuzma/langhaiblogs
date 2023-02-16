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

    /**
     * 管理员更新开发日志记录
     *
     * @param devLog
     */
    void updateDevLog(DevLog devLog);

    /**
     * 管理员删除开发日志记录
     *
     * @param id
     */
    void deleteDevLog(Long id);
}
