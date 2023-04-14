package cc.langhai.controller.navwebsite;


import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.NavWebsite;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.NavWebsiteReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.INavWebsiteService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 导航网站控制器
 *
 * @author langhai
 * @date 2023-02-25 10:42
 */
@Controller
@RequestMapping("/navWebsite")
public class NavWebsiteController {

    @Autowired
    private INavWebsiteService navWebsiteService;

    /**
     * 跳转到公共导航网站管理页面
     *
     * @return 公共导航网站管理页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navWebsiteListPage")
    public String navWebsiteListPage(Long navClassifyId, Model model){
        if (ObjectUtil.isNotNull(navClassifyId)){
            model.addAttribute("navClassifyId", navClassifyId);
        }

        return "blogs/navWebsite/navWebsiteList";
    }

    /**
     * 跳转到公共导航网站新增页面
     *
     * @return 公共导航网站新增页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navWebsiteAddPage")
    public String navWebsiteAddPage(Long navClassifyId, Model model){
        if (ObjectUtil.isNotNull(navClassifyId)){
            model.addAttribute("navClassifyId", navClassifyId);
        }

        return "blogs/navWebsite/navWebsiteAdd";
    }

    /**
     * 跳转到公共导航网站更新页面
     *
     * @return 公共导航网站更新页面
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navWebsiteUpdatePage")
    public String navWebsiteUpdatePage(Long id, Model model) {
        if(ObjectUtil.isNull(id)){
            throw new BusinessException(NavWebsiteReturnCode.NAV_WEBSITE_UPDATE_FAIL_00006);
        }
        model.addAttribute("navWebsite", navWebsiteService.getById(id));

        return "blogs/navWebsite/navWebsiteUpdate";
    }

    /**
     * 获取公共导航网站列表页面数据
     *
     * @return
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navWebsiteList")
    @ResponseBody
    public JSONObject navWebsiteList(Long navClassifyId){
        List<NavWebsite> list = null;
        if(ObjectUtil.isNotNull(navClassifyId)){
            list = navWebsiteService.list(Wrappers.<NavWebsite>lambdaQuery()
                    .eq(NavWebsite::getNavClassifyId, navClassifyId));
        }else {
            list = navWebsiteService.list();
        }
        // 收集数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", list);
        jsonObject.put("count", list.size());

        return jsonObject;
    }

    /**
     * 新增公共导航网站
     *
     * @return 新增结果
     */
    @ResponseBody
    @PostMapping("/addNavWebsite")
    @RequestAuthority(value = {"admin"})
    public ResultResponse addNavWebsite(@RequestBody @Validated NavWebsite navWebsite){
        if(ObjectUtil.isNull(navWebsite)){
            return ResultResponse.fail(NavWebsiteReturnCode.NAV_WEBSITE_ADD_FAIL_00002);
        }
        navWebsiteService.addNavWebsite(navWebsite);

        return ResultResponse.success(NavWebsiteReturnCode.NAV_WEBSITE_ADD_SUCCESS_00001);
    }

    /**
     * 更新公共导航网站
     *
     * @return 更新结果
     */
    @ResponseBody
    @PostMapping("/updateNavWebsite")
    @RequestAuthority(value = {"admin"})
    public ResultResponse updateNavWebsite(@RequestBody @Validated NavWebsite navWebsite){
        navWebsiteService.updateNavWebsite(navWebsite);

        return ResultResponse.success(NavWebsiteReturnCode.NAV_WEBSITE_UPDATE_SUCCESS_00005);
    }

    /**
     * 删除公共导航网站
     *
     * @return
     */
    @DeleteMapping("/deleteNavWebsite")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public ResultResponse deleteNavWebsite(Long id){
        if(ObjectUtil.isNull(id)){
            return ResultResponse.fail(NavWebsiteReturnCode.NAV_WEBSITE_DELETE_FAIL_00004);
        }
        navWebsiteService.deleteNavWebsite(id);

        return ResultResponse.success(NavWebsiteReturnCode.NAV_WEBSITE_DELETE_SUCCESS_00003);
    }

    /**
     * 跳转到导航网站详情信息页面
     *
     * @param id 导航网站id
     * @return 导航网站详情信息页面
     */
    @GetMapping("/navWebsiteInfo")
    public String navWebsiteInfo(String id, Model model){
        if (ObjectUtil.isNotNull(id)){
            NavWebsite website = navWebsiteService.getById(id);
            website.setAddTimeShow(DateUtil.format(website.getAddTime(), "yyyy-MM-dd HH:mm:ss"));
            model.addAttribute("website", website);
        }

        return "blogs/navWebsite/navWebsiteInfo";
    }
}
