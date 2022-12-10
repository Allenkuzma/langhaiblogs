package cc.langhai.controller;

import cc.langhai.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 首页控制器
 *
 * @author langhai
 * @date 2022-11-19 16:32
 */
@Controller
public class IndexController {

    @Autowired
    private RegisterService registerService;

    /**
     * 跳转到 浪海博客 首页
     *
     * @return 首页页面 blogs/index.html
     */
    @GetMapping("/")
    public String index(HttpServletRequest httpRequest, HttpSession session){
        registerService.remember(httpRequest, session);
        return "blogs/index";
    }
}
