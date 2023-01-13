package cc.langhai.controller;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.config.constant.LinksConstant;
import cc.langhai.domain.Links;
import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.service.LinksService;
import cc.langhai.service.RegisterService;
import cc.langhai.service.UserInfoService;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Autowired
    private LinksService linksService;

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

        // 首页超链接
        List<Links> linksList = linksService.list();
        if(linksList.size() < LinksConstant.LINKS_COUNT_ALL){
            for (int i = 0; i < LinksConstant.LINKS_COUNT_ALL - linksList.size(); i++) {
                Links links = new Links();
                links.setTitle("请在系统中填充满六个友情链接");
                links.setDescription("请在系统中填充满六个友情链接");
                linksList.add(links);
            }
        }
        model.addAttribute("linksList", linksList);

        return "blogs/index";
    }

}
