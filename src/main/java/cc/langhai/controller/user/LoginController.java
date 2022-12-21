package cc.langhai.controller.user;

import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.RegisterService;
import cc.langhai.utils.ImageVerifyCodeGenerator;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 用户登录控制器
 *
 * @author langhai
 * @date 2022-11-20 15:20
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private RegisterService registerService;

    /**
     * 跳转到 登录 页面
     *
     * @return
     */
    @GetMapping("/loginPage")
    public String loginPage(HttpSession session){
        Object user = session.getAttribute("user");
        if(ObjectUtil.isNotNull(user)){
            return "blogs/index";
        }

        return "blogs/user/login";
    }

    /**
     * 生成验证码图片
     *
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        //生成验证码图片
        Map<String, Object> imageVerify = ImageVerifyCodeGenerator.generate(300, 40 ,4);
        BufferedImage image = (BufferedImage) imageVerify.get("image");
        String code = String.valueOf(imageVerify.get("code"));
        session.setAttribute("verifyCode", code);
        //图片验证码输出到响应流
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 用户登录功能
     *
     * @param username
     * @param password
     * @param verifyCodeText
     * @param session
     * @return
     */
    @PostMapping("/loginEnter")
    @ResponseBody
    public ResultResponse loginEnter(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("verifyCodeText") String verifyCodeText,
                             HttpSession session, HttpServletResponse response,
                             String remember){
        registerService.loginEnter(username, password, verifyCodeText, session, remember, response);
        return ResultResponse.success(UserReturnCode.USER_LOGIN_ENTER_YES_00014);
    }

    /**
     * 用户退出
     *
     * @param session
     * @return
     */
    @PostMapping("/loginOut")
    @ResponseBody
    public ResultResponse loginOut(HttpSession session, HttpServletResponse response){
        registerService.loginOut(session, response);
        return ResultResponse.success(UserReturnCode.USER_LOGOUT_YES_00017);
    }
}
