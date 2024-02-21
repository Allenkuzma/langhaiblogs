package cc.langhai.service.system;

import com.alibaba.fastjson.JSONObject;

/**
 * 后台通知 service接口
 *
 * @author langhai
 * @date 2024-02-21 15:54
 */
public interface NotificationService {

    /**
     * 新增后台通知
     *
     * @param notification 通知内容
     * @param seconds 显示时间单位秒钟
     */
    void addNotification(String notification, String seconds);

    /**
     * 获取所有后台通知
     *
     * @return 所有后台通知
     */
    JSONObject getAllNotification();

}
