package cc.langhai.controller.devlog;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.DevLog;
import cc.langhai.response.DevLogReturnCode;
import cc.langhai.response.ResultResponse;
import cc.langhai.service.DevLogService;
import cn.hutool.core.util.ObjectUtil;
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

        return "blogs/devLog/devLogShow";
    }

    /**
     * 跳转到开发日志记录管理页面
     *
     * @return
     */
    @GetMapping("/devLogListPage")
    @RequestAuthority(value = {"admin"})
    public String devLogManage(){

        return "blogs/devLog/devLogList";
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
     * 跳转到开发日志记录更新页面
     *
     * @return
     */
    @GetMapping("/devLogUpdatePage")
    @RequestAuthority(value = {"admin"})
    public String devLogUpdatePage(Long id, String title, String content, Model model){
        model.addAttribute("id", id);
        model.addAttribute("title", title);
        model.addAttribute("content", content);

        return "blogs/devLog/devLogUpdate";
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
        // 分页查询日志记录数据
        PageHelper.startPage(page, limit);
        List<DevLog> devLogList = devLogService.list();
        PageInfo<DevLog> devLogPageInfo = new PageInfo<>(devLogList);
        // 数据收集返回
        JSONObject jsonObject = new JSONObject();
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

    /**
     * 更新开发日志记录
     *
     * @return
     */
    @PostMapping("/updateDevLog")
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    public ResultResponse updateDevLog(@RequestBody @Validated DevLog devLog){
        if(ObjectUtil.isNull(devLog.getId()) || StrUtil.isBlank(devLog.getContent())){
            return ResultResponse.fail(DevLogReturnCode.DEV_LOG_UPDATE_FAIL_00004);
        }
        devLogService.updateDevLog(devLog);

        return ResultResponse.success(DevLogReturnCode.DEV_LOG_UPDATE_SUCCESS_00003);
    }

    /**
     * 管理员删除开发日志记录
     *
     * @return
     */
    @DeleteMapping("/deleteDevLog")
    @ResponseBody
    @RequestAuthority(value = {"admin"})
    public ResultResponse deleteDevLog(Long id){
        if(ObjectUtil.isNull(id)){
            return ResultResponse.fail(DevLogReturnCode.DEV_LOG_DELETE_FAIL_00006);
        }
        devLogService.deleteDevLog(id);

        return ResultResponse.success(DevLogReturnCode.DEV_LOG_DELETE_SUCCESS_00005);
    }
}
