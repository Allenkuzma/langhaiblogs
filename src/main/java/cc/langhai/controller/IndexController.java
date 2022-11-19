package cc.langhai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 *
 * @author langhai
 * @date 2022-11-19 16:32
 */
@Controller
public class IndexController {

    /**
     * 跳转到 浪海博客 首页
     *
     * @return 首页页面 blogs/index.html
     */
    @GetMapping("/")
    public String index(){
        return "blogs/index";
    }
}
