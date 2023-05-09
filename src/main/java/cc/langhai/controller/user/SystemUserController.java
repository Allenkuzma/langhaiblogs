package cc.langhai.controller.user;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统管理用户控制器
 *
 * @author langhai
 * @date 2023-04-26 11:07
 */
@Controller
@RequestMapping("/system/user")
public class SystemUserController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到管理员用户管理页面
     *
     * @return 用户管理页面
     */
    @GetMapping("/userPage")
    @RequestAuthority(value = {"admin"})
    public String userPage(){

        return "blogs/user/systemUser";
    }

    /**
     * 修改启用状态
     *
     * @param id 用户id
     * @param enable 启用状态
     * @return 修改启用结果
     */
    @ResponseBody
    @PutMapping("/enable")
    @RequestAuthority(value = {"admin"})
    public ResultResponse enable(Long id, Boolean enable){
        userService.enable(id, enable);

        return ResultResponse.success(UserReturnCode.USER_UPDATE_ENABLE_SUCCEED_00024);
    }

    /**
     * 跳转到用户被禁用页面
     *
     * @return 用户被禁用页面
     */
    @GetMapping("/userEnablePage")
    public String userEnablePage(){

        return "error/enable";
    }

    /**
     * 修改用户图库功能状态
     *
     * @param id 用户id
     * @param image 图库功能状态
     * @return 修改用户图库功能状态结果
     */
    @ResponseBody
    @PutMapping("/image")
    @RequestAuthority(value = {"admin"})
    public ResultResponse image(Long id, Boolean image){
        userService.image(id, image);

        return ResultResponse.success(UserReturnCode.USER_UPDATE_IMAGE_SUCCEED_00026);
    }
}
