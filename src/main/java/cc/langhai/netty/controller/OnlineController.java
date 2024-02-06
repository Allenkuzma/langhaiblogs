package cc.langhai.netty.controller;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.utils.UserContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 在线客服聊天控制器
 *
 * @author langhai
 * @date 2023-03-23 19:29
 */
@Controller
public class OnlineController {

    @Value("${netty.ws}")
    private String ws;

    /**
     * 客服界面
     */
    @GetMapping(value = { "/netty/customer"})
    @RequestAuthority(value = {"admin"})
    public String index(Model model) {
        model.addAttribute("ws", ws);
        model.addAttribute("userId", UserContext.getUserId());

        return "netty/admin";
    }

    /**
     * 游客页面
     */
    @GetMapping("/netty/tourist")
    public String tourist(Model model) {
        model.addAttribute("ws", ws);
        model.addAttribute("userId", UserContext.getUserId());

        return "netty/user";
    }
}
