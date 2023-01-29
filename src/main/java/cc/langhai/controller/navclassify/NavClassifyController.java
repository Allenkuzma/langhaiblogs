package cc.langhai.controller.navclassify;


import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.NavClassify;
import cc.langhai.domain.NavWebsite;
import cc.langhai.service.INavClassifyService;
import cc.langhai.service.INavWebsiteService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

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
     * 跳转到公共导航页面
     *
     * @return
     */
    @GetMapping("/navPage")
    public String navPage(Model model){
        // 获取公共导航页面网站数据
        List<NavClassify> list = navClassifyService.getPublicNav();
        model.addAttribute("navClassifyList", list);
        return "blogs/nav/nav";
    }

    /**
     * 跳转到公共导航分类管理页面
     *
     * @return
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navClassifyListPage")
    public String navClassifyListPage(String message, Model model){
        if(StrUtil.isNotBlank(message)){
            model.addAttribute("message", message);
        }

        return "blogs/nav/navList";
    }

    /**
     * 获取公共导航分类列表页面数据
     *
     * @return
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navClassifyList")
    @ResponseBody
    public JSONObject navClassifyList(){
        JSONObject jsonObject = new JSONObject();
        List<NavClassify> list = navClassifyService.list(Wrappers.<NavClassify>lambdaQuery()
                .eq(NavClassify::getUserId, 0));
        jsonObject.put("code", 0);
        jsonObject.put("data", list);
        jsonObject.put("count", list.size());
        return jsonObject;
    }

}
