package cc.langhai.controller.system;

import cc.langhai.config.annotation.RepeatSubmit;
import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.system.NotificationService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * 后台通知管理控制器
 *
 * @author langhai
 * @date 2024-02-21 15:15
 */
@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 跳转到后台通知管理页面
     *
     * @return 后台通知管理页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/notificationListPage")
    public String notificationListPage() {
        return "system/notification/notificationList";
    }

    /**
     * 跳转到后台通知新增页面
     *
     * @return 后台通知新增页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/notificationAddPage")
    public String notificationAddPage() {
        return "system/notification/notificationAdd";
    }

    /**
     * 获取后台通知列表页面数据
     *
     * @return 后台通知列表页面数据
     */
    @ResponseBody
    @GetMapping("/notificationList")
    @RequestAuthority(value = {"admin"})
    public JSONObject notificationList() {
        ArrayList<JSONObject> notificationList = notificationService.list();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", notificationList);
        jsonObject.put("count", notificationList.size());
        return jsonObject;
    }

    /**
     * 新增后台通知
     *
     * @param notification 通知内容
     * @param seconds 显示时间单位秒钟
     * @return 新增后台通知结果
     */
    @RepeatSubmit
    @ResponseBody
    @PostMapping("/addNotification")
    @RequestAuthority(value = {"admin"})
    public ResultResponse<Void> addNotification(@RequestParam(value = "notification") String notification,
                                                @RequestParam(value = "seconds") String seconds,
                                                @RequestParam(value = "href") String href) {
        notificationService.addNotification(notification, seconds, href);
        return new ResultResponse(200, "新增后台通知成功。", null);
    }

    /**
     * 删除后台通知
     *
     * @param key 后台通知key
     * @return 删除后台通知结果
     */
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    @DeleteMapping("/deleteNotification")
    public ResultResponse<Void> deleteNotification(String key) {
        notificationService.deleteNotification(key);
        return new ResultResponse(200, "删除后台通知成功。", null);
    }
}
