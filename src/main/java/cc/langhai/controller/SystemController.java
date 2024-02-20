package cc.langhai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 后台管理控制器
 *
 * @author langhai
 * @date 2024-02-20 17:16
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    /**
     * 跳转到浪海博客后台管理首页
     *
     * @return 后台管理首页页面 system/index.html
     */
    @GetMapping(value = "/index")
    public String index(HttpServletRequest httpRequest, HttpSession session, Model model) {
        return "system/index";
    }
}
