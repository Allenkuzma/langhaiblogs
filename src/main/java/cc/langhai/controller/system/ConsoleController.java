package cc.langhai.controller.system;

import cc.langhai.config.system.SystemConfig;
import cc.langhai.domain.Article;
import cc.langhai.domain.User;
import cc.langhai.service.ArticleService;
import cc.langhai.service.UserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;
import java.util.List;

/**
 * 控制后台管理控制器
 *
 * @author langhai
 * @date 2024-02-21 11:44
 */
@Controller
@RequestMapping("/system")
public class ConsoleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    /**
     * 跳转到浪海博客控制后台页面
     *
     * @return 控制后台页面 system/console/console1.html
     */
    @GetMapping(value = "/console1")
    public String console1(HttpServletRequest httpRequest, HttpSession session, Model model) {
        PageHelper.startPage(1, 10);
        // 用户最近十篇文章
        List<Article> articleList = articleService.getAllArticle(null, null, "user");
        model.addAttribute("articleList", articleList);
        PageHelper.startPage(1, 3);
        // 获取最近注册用户
        List<User> userList = userService.getUserList("", "");
        model.addAttribute("userList", userList);
        model.addAttribute("slogan", SystemConfig.INDEX_SLOGAN);
        return "system/console/console1";
    }
}
