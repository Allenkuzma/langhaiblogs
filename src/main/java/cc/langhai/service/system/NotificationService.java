package cc.langhai.service.system;

import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.util.ArrayList;

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
     * @param href 通知跳转地址
     */
    void addNotification(String notification, String seconds, String href);

    /**
     * 获取所有后台通知
     *
     * @return 所有后台通知
     */
    JSONObject getAllNotification();


    /**
     * 获取后台通知列表页面数据
     *
     * @return 后台通知列表页面数据
     */
    ArrayList<JSONObject> list();

    /**
     * 删除后台通知
     *
     * @param key 后台通知key
     */
    void deleteNotification(String key);

}
