package cc.langhai.controller.links;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.Article;
import cc.langhai.domain.Label;
import cc.langhai.domain.Links;
import cc.langhai.domain.Message;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.response.LinksReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
import cc.langhai.service.LabelService;
import cc.langhai.service.LinksService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * 首页超链接管理控制器
 *
 * @author langhai
 * @date 2023-01-12 13:36
 */
@Controller
@RequestMapping("/links")
public class LinksController {

    @Autowired
    private LinksService linksService;

    /**
     * 跳转到友情链接管理页面
     *
     * @return 友情链接管理页面
     */
    @GetMapping("/linksListPage")
    @RequestAuthority(value = {"admin"})
    public String linksListPage(){

        return "blogs/links/linksList";
    }

    /**
     * 跳转到友情链接新增页面
     *
     * @return 友情链接新增页面
     */
    @GetMapping("/linksAddPage")
    @RequestAuthority(value = {"admin"})
    public String linksAddPage(){

        return "blogs/links/linksAdd";
    }

    /**
     * 跳转到友情链接更新页面
     *
     * @return 友情链接更新页面
     */
    @GetMapping("/linksUpdatePage")
    @RequestAuthority(value = {"admin"})
    public String linksUpdatePage(Long id, Model model){
        if(ObjectUtil.isNull(id)){
            throw new BusinessException(LinksReturnCode.LINKS_UPDATE_FAIL_00006);
        }

        Links links = linksService.getById(id);
        model.addAttribute("links", links);

        return "blogs/links/linksUpdate";
    }

    /**
     * 获取友情链接列表页面数据
     *
     * @return 友情链接列表页面数据
     */
    @ResponseBody
    @GetMapping("/linksList")
    @RequestAuthority(value = {"admin"})
    public JSONObject linksList(){
        List<Links> linksList = linksService.list();
        Collections.sort(linksList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("data", linksList);
        jsonObject.put("count", linksList.size());

        return jsonObject;
    }

    /**
     * 新增友情链接
     *
     * @return 新增友情链接结果
     */
    @ResponseBody
    @PostMapping("/addLinks")
    @RequestAuthority(value = {"admin"})
    public ResultResponse addLinks(@RequestBody @Validated Links links){
        if(ObjectUtil.isNull(links)){
            return ResultResponse.fail(LinksReturnCode.LINKS_ADD_FAIL_00002);
        }

        linksService.addLinks(links);

        return ResultResponse.success(LinksReturnCode.LINKS_ADD_SUCCESS_00001);
    }

    /**
     * 删除友情链接
     *
     * @return
     */
    @ResponseBody
    @DeleteMapping("/deleteLinks")
    @RequestAuthority(value = {"admin"})
    public ResultResponse deleteLinks(Long id){
        if(ObjectUtil.isNull(id)){
            return ResultResponse.fail(LinksReturnCode.LINKS_DELETE_FAIL_00004);
        }

        linksService.deleteLinks(id);

        return ResultResponse.success(LinksReturnCode.LINKS_DELETE_SUCCESS_00003);
    }

    /**
     * 更新友情链接
     *
     * @return 更新友情链接结果
     */
    @ResponseBody
    @PostMapping("/updateLinks")
    @RequestAuthority(value = {"admin"})
    public ResultResponse updateLinks(@RequestBody @Validated Links links){
        linksService.updateLinks(links);

        return ResultResponse.success(LinksReturnCode.LINKS_UPDATE_SUCCESS_00005);
    }

}
 