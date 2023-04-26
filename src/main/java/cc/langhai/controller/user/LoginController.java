package cc.langhai.controller.user;

import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.RegisterService;
import cc.langhai.utils.ImageVerifyCodeGenerator;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
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
     * 跳转到登录页面
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
        Map<String, Object> imageVerify = ImageVerifyCodeGenerator.generate(300, 45 ,4);
        BufferedImage image = (BufferedImage) imageVerify.get("image");
        String code = String.valueOf(imageVerify.get("code"));
        session.setAttribute("verifyCode", code);
        //图片验证码输出到响应流
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 用户登录功能
     *
     * @param username        用户账号
     * @param password        用户密码
     * @param verifyCodeText  验证码内容
     * @param remember        记住我功能 七天免登录
     * @return 用户登录处理的结果
     */
    @ResponseBody
    @PostMapping("/loginEnter")
    public ResultResponse loginEnter(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("verifyCodeText") String verifyCodeText,
                             @RequestParam(value = "remember", required = false) String remember,
                             HttpSession session, HttpServletResponse response){
        // 用户登录服务处理
        registerService.loginEnter(username, password, verifyCodeText, session, remember, response);
        // 用户登录成功
        return ResultResponse.success(UserReturnCode.USER_LOGIN_ENTER_YES_00014);
    }

    /**
     * 用户退出
     *
     * @param session http session
     * @return 用户退出结果
     */
    @PostMapping("/loginOut")
    @ResponseBody
    public ResultResponse loginOut(HttpSession session, HttpServletResponse response){
        registerService.loginOut(session, response);

        return ResultResponse.success(UserReturnCode.USER_LOGOUT_YES_00017);
    }

    /**
     * 判断用户是否登录
     *
     */
    @GetMapping("/enter")
    @ResponseBody
    public JSONObject enter(HttpServletRequest request){

        return registerService.enter(request);
    }

}
