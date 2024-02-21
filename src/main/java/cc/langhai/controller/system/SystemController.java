package cc.langhai.controller.system;

import cc.langhai.service.system.NotificationService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台管理控制器
 *
 * @author langhai
 * @date 2024-02-20 17:16
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 跳转到浪海博客后台管理首页
     *
     * @return 后台管理首页页面 system/index.html
     */
    @GetMapping(value = "/index")
    public String index(HttpServletRequest httpRequest, HttpSession session, Model model) {
        return "system/index";
    }

    /**
     * message.json获取消息通知
     *
     * @return message.json
     */
    @ResponseBody
    @GetMapping(value = "/message")
    public JSONArray message(HttpServletRequest httpRequest, HttpSession session, Model model) {
        JSONObject notification = notificationService.getAllNotification();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(notification);
        return jsonArray;
    }
}
