package cc.langhai.controller.navclassify;


import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.config.constant.IconfontConstant;
import cc.langhai.domain.NavClassify;
import cc.langhai.domain.NavWebsite;
import cc.langhai.response.NavClassifyReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.INavClassifyService;
import cc.langhai.service.INavWebsiteService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
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
     * 跳转到公共导航分类新增页面
     *
     * @return
     */
    @RequestAuthority(value = {"admin"})
    @GetMapping("/navClassifyAddPage")
    public String navClassifyAddPage(Model model) throws IllegalAccessException {
        ArrayList<String> iconfont = new ArrayList<>();
        // 遍历输出属性
        Field[] fields =  IconfontConstant.class.getDeclaredFields();
        for( int i = 0; i < fields.length; i++){
            Field f = fields[i];
            iconfont.add(f.get(IconfontConstant.class).toString());
        }

        model.addAttribute("iconfont", iconfont);
        return "blogs/nav/navAdd";
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

    /**
     * 新增公共导航分类
     *
     * @return
     */
    @PostMapping("/addNav")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public ResultResponse addNav(@RequestBody @Validated NavClassify navClassify){
        if(ObjectUtil.isNull(navClassify)){
            return ResultResponse.fail(NavClassifyReturnCode.NAV_CLASSIFY_ADD_FAIL_00002);
        }

        navClassifyService.addNavClassify(navClassify);
        return ResultResponse.success(NavClassifyReturnCode.NAV_CLASSIFY_ADD_SUCCESS_00001);
    }

    /**
     * 删除公共导航分类
     *
     * @return
     */
    @PostMapping("/deleteNav")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public ResultResponse deleteNav(Long id){
        if(ObjectUtil.isNull(id)){
            return ResultResponse.fail(NavClassifyReturnCode.NAV_CLASSIFY_DELETE_FAIL_00006);
        }

        navClassifyService.deleteNavClassify(id);
        return ResultResponse.success(NavClassifyReturnCode.NAV_CLASSIFY_DELETE_SUCCESS_00005);
    }

}
