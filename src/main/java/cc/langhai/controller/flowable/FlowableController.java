package cc.langhai.controller.flowable;

import cc.langhai.response.ResultResponse;
import cc.langhai.service.IFlowableService;
import com.alibaba.fastjson.JSONObject;
import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程引擎控制器
 *
 * @author langhai
 * @date 2023-03-28 11:54
 */
@RestController
@RequestMapping("/flowableTest")
public class FlowableController {

    @Autowired
    private IFlowableService flowableService;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 启动实例流程
     * @return
     */
    @PostMapping("/startProcess")
    public ResultResponse startProcess(@RequestParam(name = "employee") String employee,
                                       @RequestParam(name = "nrOfHolidays") Integer nrOfHolidays,
                                       @RequestParam(name = "description") String description){
        flowableService.startProcess(employee, nrOfHolidays, description);

        return ResultResponse.success();
    }

    /**
     * 删除流程定义
     * @return
     */
    @DeleteMapping("/deleteDeploy")
    public ResultResponse deleteDeploy(@RequestParam(name = "deploymentId") String deploymentId){
        // 如果流程启动了，相关的任务一并会被删除
        repositoryService.deleteDeployment(deploymentId, true);

        return ResultResponse.success();
    }

    /**
     * 获取指定工作人的代办任务
     * @param assignee
     * @return
     */
    @GetMapping("/getTaskInstance/{assignee}")
    public ResultResponse taskInstance(@PathVariable("assignee") String assignee){
        flowableService.taskInstance(assignee);

        return ResultResponse.success();
    }

    /**
     * 执行任务
     * @return
     */
    @PutMapping("/handleTask")
    public ResultResponse handleTask(@RequestParam(name = "approved") String approved,
                                     @RequestParam(name = "assignee") String assignee){
        flowableService.handleTask(approved, assignee);

        return ResultResponse.success();
    }

    /**
     * 获取历史审批数据
     * @return
     */
    @GetMapping("/queryHistoryProcess")
    public ResultResponse queryHistoryProcess(String assignee){
        flowableService.queryHistoryProcess(assignee);

        return ResultResponse.success();
    }

}
