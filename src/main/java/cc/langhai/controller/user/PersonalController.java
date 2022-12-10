package cc.langhai.controller.user;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 用户个人空间 控制器
 *
 * @author langhai
 * @date 2022-12-10 16:30
 */
@Controller
@RequestMapping("/user")
public class PersonalController {


    /**
     * 跳转到 个人空间 页面
     *
     * @return
     */
    @GetMapping("/personalPage")
    public String loginPage(HttpSession session){


        return "blogs/user/personal";
    }

}
