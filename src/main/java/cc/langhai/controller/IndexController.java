package cc.langhai.controller;

import cc.langhai.config.constant.LinksConstant;
import cc.langhai.config.system.SystemConfig;
import cc.langhai.domain.Links;
import cc.langhai.domain.User;
import cc.langhai.service.LinksService;
import cc.langhai.service.RegisterService;
import cc.langhai.service.RoleService;
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

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到浪海博客首页
     *
     * @return 首页页面 blogs/index.html
     */
    @GetMapping(value = {"/", "/index"})
    public String index(HttpServletRequest httpRequest, HttpSession session, Model model) {
        registerService.remember(httpRequest, session);
        // 存储用户详情信息
        User user = (User) session.getAttribute("user");
        if (ObjectUtil.isNotNull(user)) {
            roleService.determineAdmin(user);
            model.addAttribute("userInfo", userInfoService.getUserInfoById(user.getId()));
        }
        // 首页超链接
        List<Links> linksList = linksService.getSortAllLinks();
        model.addAttribute("linksList", linksList);
        // 底部友情链接 超过六个放在底部
        if (linksList.size() > LinksConstant.LINKS_COUNT_ALL) {
            model.addAttribute("bottomLinks", linksList.subList(6, linksList.size()));
        }
        // 首页友情链接标识语
        model.addAttribute("slogan", SystemConfig.INDEX_SLOGAN);
        return "blogs/index";
    }

}
