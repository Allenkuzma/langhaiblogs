package cc.langhai.controller;

import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.service.RegisterService;
import cc.langhai.service.UserInfoService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 跳转到 浪海博客 首页
     *
     * @return 首页页面 blogs/index.html
     */
    @GetMapping("/")
    public String index(HttpServletRequest httpRequest, HttpSession session, Model model){
        registerService.remember(httpRequest, session);

        // 存储用户详情信息
        Long userId = null;
        User user = (User) session.getAttribute("user");
        if(ObjectUtil.isNotNull(user)){
            userId = user.getId();
        }

        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        model.addAttribute("userInfo", userInfo);

        return "blogs/index";
    }
}
