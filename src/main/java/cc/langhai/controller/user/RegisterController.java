package cc.langhai.controller.user;

import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
    private RegisterService registerService;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("/registerPage")
    public String registerPage(){

        return "blogs/user/register";
    }

    /**
     * 发送注册邮箱验证码
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/sendEmailCode")
    public ResultResponse sendEmailCode(@RequestParam("email") String email, HttpServletRequest request){
        registerService.sendEmailCode(email, request);

        return ResultResponse.success(UserReturnCode.EMAIL_CODE_00000);
    }

    /**
     * 校验用户名的唯一性
     *
     * @return
     */
    @PostMapping("/verifyUsername")
    @ResponseBody
    public ResultResponse verifyUsername(@RequestParam("username") String username){
        registerService.verifyUsername(username);
        return ResultResponse.success(UserReturnCode.USER_NAME_SINGLE_OK_00007);
    }

    /**
     * 用户注册
     *
     * @return 注册结果
     */
    @ResponseBody
    @PostMapping("/register")
    public ResultResponse register(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("nickname") String nickname,
                                   @RequestParam("email") String email,
                                   @RequestParam("verifyCodeText") String verifyCodeText, HttpSession session, HttpServletResponse response){
        registerService.register(username, password, nickname, email, verifyCodeText, session, response);

        return ResultResponse.success(UserReturnCode.USER_REGISTER_SUCCESS_00010);
    }


}
