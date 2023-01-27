package cc.langhai.controller.navclassify;


import cc.langhai.domain.NavClassify;
import cc.langhai.domain.NavWebsite;
import cc.langhai.service.INavClassifyService;
import cc.langhai.service.INavWebsiteService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航分类控制器
 *
 * @author langhai
 * @date 2023-01-27 16:10
 */
@Controller
@RequestMapping("/navClassify")
public class NavClassifyController {

    @Autowired
    private INavClassifyService navClassifyService;

    @Autowired
    private INavWebsiteService navWebsiteService;

    /**
     * 跳转到导航页面
     *
     * @return
     */
    @GetMapping("/navPage")
    public String navPage(Model model){
        List<NavClassify> list = navClassifyService.list();
        model.addAttribute("navClassifyList", list);

        if(CollUtil.isNotEmpty(list)){
            for (NavClassify navClassify : list) {
                List<NavWebsite> navWebsiteList = navWebsiteService.list(Wrappers.<NavWebsite>lambdaQuery()
                        .eq(NavWebsite::getNavClassifyId, navClassify.getId()));
                navClassify.setWebsiteList(navWebsiteList);

                model.addAttribute("navList", list);
            }
        }

        return "blogs/nav/nav";
    }

}
