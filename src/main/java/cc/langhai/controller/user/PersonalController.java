package cc.langhai.controller.user;

import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.service.UserInfoService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户个人空间 控制器
 *
 * @author langhai
 * @date 2022-12-10 16:30
 */
@Controller
@RequestMapping("/user")
public class PersonalController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 跳转到 个人空间 页面
     *
     * @return
     */
    @GetMapping("/personalPage")
    public String loginPage(HttpSession session, Model model){
        // 将用户详情信息存储到 model中
        User user = UserContext.get();
        UserInfo userInfo = userInfoService.getUserInfoById(user.getId());

        // 计算两个日期的时间差
        long between = DateUtil.between(user.getAddTime(), new Date(), DateUnit.DAY);
        userInfo.setDay(between);

        model.addAttribute("userInfo", userInfo);

        return "blogs/user/personal";
    }


    /**
     * 跳转到 更新用户个人信息 页面
     *
     * @return
     */
    @GetMapping("/updateUserInfoPage")
    public String updateUserInfoPage(HttpSession session, Model model){

        return "blogs/user/updateUserInfo";
    }
}
