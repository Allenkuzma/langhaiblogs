package cc.langhai.controller.user;

import cc.langhai.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 用户注册控制器
 *
 * @author langhai
 * @date 2022-11-21 20:26
 */
@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 跳转到 注册页面
     *
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "blogs/user/register";
    }

    /**
     * 发送注册邮箱验证码
     *
     * @return
     */
    @PostMapping("/sendEmailCode")
    @ResponseBody
    public String sendEmailCode(@RequestParam("email") String email){
        emailUtil.send(email);
        return "200";
    }

}
