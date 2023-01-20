package cc.langhai.controller.devlog;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.DevLog;
import cc.langhai.domain.Label;
import cc.langhai.response.DevLogReturnCode;
import cc.langhai.response.LabelReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.ArticleService;
import cc.langhai.service.DevLogService;
import cc.langhai.service.LabelService;
import cc.langhai.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 开发日志记录控制器
 *
 * @author langhai
 * @date 2022-01-16 18:44
 */
@Controller
@RequestMapping("/devLog")
public class DevLogController {

    @Autowired
    private DevLogService devLogService;

    /**
     * 跳转到展示开发日志记录页面
     *
     * @return
     */
    @GetMapping("/devLogPage")
    public String devLogPage(Model model){
        List<DevLog> list = devLogService.list(Wrappers.<DevLog>lambdaQuery().orderByAsc(DevLog::getAddTime));
        model.addAttribute("list", list);
        return "blogs/devLog/devLogList";
    }

    /**
     * 跳转到开发日志记录管理页面
     *
     * @return
     */
    @GetMapping("/devLogManagePage")
    @RequestAuthority(value = {"admin"})
    public String devLogManage(){
        return "blogs/devLog/devLogManage";
    }

    /**
     * 跳转到开发日志记录新增页面
     *
     * @return
     */
    @GetMapping("/devLogAddPage")
    @RequestAuthority(value = {"admin"})
    public String devLogAddPage(){
        return "blogs/devLog/devLogAdd";
    }

    /**
     * 获取开发日志记录列表页面数据
     *
     * @return
     */
    @GetMapping("/devLogList")
    @RequestAuthority(value = {"admin"})
    @ResponseBody
    public JSONObject devLogList(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit){
        JSONObject jsonObject = new JSONObject();
        PageHelper.startPage(page, limit);

        List<DevLog> devLogList = devLogService.list();
        PageInfo<DevLog> devLogPageInfo = new PageInfo<>(devLogList);

        jsonObject.put("code", 0);
        jsonObject.put("data", devLogList);
        jsonObject.put("count", devLogPageInfo.getTotal());
        return jsonObject;
    }

    /**
     * 新增开发日志记录
     *
     * @return
     */
    @PostMapping("/addDevLog")
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    public ResultResponse addDevLog(@RequestBody @Validated DevLog devLog){

        devLogService.addDevLog(devLog);
        return ResultResponse.success(DevLogReturnCode.DEV_LOG_ADD_SUCCESS_00001);
    }


}
