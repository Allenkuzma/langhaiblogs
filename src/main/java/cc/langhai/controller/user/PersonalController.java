package cc.langhai.controller.user;

import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.ArticleService;
import cc.langhai.service.LabelService;
import cc.langhai.service.PersonalService;
import cc.langhai.service.UserInfoService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 用户个人空间控制器
 *
 * @author langhai
 * @date 2022-12-10 16:30
 */
@Controller
@RequestMapping("/user")
public class PersonalController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private LabelService labelService;

    @Autowired
    private ArticleService articleService;

    /**
     * 跳转到个人空间页面
     *
     * @return 个人空间页面 blogs/user/personal
     */
    @GetMapping("/personalPage")
    public String personalPage(Model model) {
        // 将用户详情信息存储到model中
        User user = UserContext.get();
        UserInfo userInfo = userInfoService.getUserInfoById(user.getId());
        // 计算两个日期的时间差
        long between = DateUtil.between(user.getAddTime(), new Date(), DateUnit.DAY);
        userInfo.setDay(between);
        model.addAttribute("userInfo", userInfo);
        return "blogs/user/personal";
    }

    /**
     * 跳转到更新用户个人信息页面
     *
     * @return 更新用户个人信息页面 blogs/user/updateUserInfo
     */
    @GetMapping("/updateUserInfoPage")
    public String updateUserInfoPage(Model model){
        // 将用户详情信息存储到model中
        UserInfo userInfo = userInfoService.getUserInfoById(UserContext.getUserId());
        model.addAttribute("userInfo", userInfo);
        return "blogs/user/updateUserInfo";
    }

    /**
     * 更新用户个人信息，提交至数据库。
     *
     * @return 更新用户个人信息结果
     */
    @ResponseBody
    @PostMapping("/updateUserInfo")
    public ResultResponse updateUserInfo(@RequestParam("nickname") String nickname,
                                         @RequestParam("motto") String motto, HttpSession session) {
        personalService.updateUserInfo(nickname, motto, session);
        return ResultResponse.success(UserReturnCode.USER_PERSONAL_UPDATE_USER_INFO_YES_00018);
    }

    /**
     * 跳转到个人空间页面-后台系统
     *
     * @return blogs/user/person 后台系统个人空间页面
     */
    @GetMapping("/personPage")
    public String personPage(Model model){
        // 将用户详情信息存储到model中
        UserInfo userInfo = userInfoService.getUserInfoById(UserContext.getUserId());
        model.addAttribute("userInfo", userInfo);
        // 用户标签信息
        model.addAttribute("labelList", labelService.getAllLabelByUser());
        PageHelper.startPage(1, 10);
        // 用户最近十篇文章
        List<Article> articleList = articleService.getAllArticle(null, null, "user");
        model.addAttribute("articleList", articleList);
        return "blogs/user/person";
    }
}
