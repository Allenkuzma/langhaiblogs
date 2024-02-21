package cc.langhai.controller.system;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.Links;
import cc.langhai.response.LinksReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.system.NotificationService;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 新增后台通知
     *
     * @param notification 通知内容
     * @param seconds 显示时间单位秒钟
     * @return 新增后台通知结果
     */
    @ResponseBody
    @PostMapping("/addNotification")
    @RequestAuthority(value = {"admin"})
    public ResultResponse<Void> addNotification(@RequestParam(value = "notification") String notification,
                                                @RequestParam(value = "seconds") String seconds) {
        notificationService.addNotification(notification, seconds);
        return new ResultResponse(200, "新增后台通知成功。", null);
    }
}
