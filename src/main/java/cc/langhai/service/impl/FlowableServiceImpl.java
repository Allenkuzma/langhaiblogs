package cc.langhai.service.impl;

import cc.langhai.service.IFlowableService;
import cn.hutool.core.collection.CollUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程引擎 service接口 实现类
 *
 * @author langhai
 * @date 2023-03-28 14:29
 */
@Service
public class FlowableServiceImpl implements IFlowableService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Override
    public void startProcess(String employee, Integer nrOfHolidays, String description) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        // 启动实例流程
        ProcessInstance holidayRequest = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        System.out.println("holidayRequest.getDeploymentId() = " + holidayRequest.getDeploymentId());
        System.out.println("holidayRequest.getProcessInstanceId() = " + holidayRequest.getProcessInstanceId());
        System.out.println("holidayRequest.getActivityId() = " + holidayRequest.getActivityId());
    }

    @Override
    public void taskInstance(String assignee) {
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee(assignee).orderByTaskCreateTime().desc().list();
        if(CollUtil.isNotEmpty(taskList)){
            taskList.stream().forEach(task -> {
                System.out.println("task.getName() = " + task.getName());
                System.out.println("task.getAssignee() = " + task.getAssignee());
                System.out.println("task.getId() = " + task.getId());

                Map<String, Object> processVariables = taskService.getVariables(task.getId());
                System.out.println(processVariables.get("employee").toString());
                System.out.println(processVariables.get("nrOfHolidays").toString());
                System.out.println(processVariables.get("description").toString());
            });
        }

    }

    @Override
    public void handleTask(String approved, String assignee) {
        Map<String, Object> taskVariables = new HashMap<>();
        boolean approvedFlag = "Y".equals(approved) ? true : false;
        taskVariables.put("approved", approvedFlag);
        // 根据流程id和处理人 获取任务对象
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holidayRequest")
                .taskAssignee(assignee)
                .singleResult();
        try {
            // 完成任务
            taskService.complete(task.getId(), taskVariables);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void queryHistoryProcess(String assignee) {
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                // .processInstanceId("")
                .taskAssignee(assignee)
                .finished().orderByHistoricActivityInstanceEndTime().desc().list();
        for(HistoricActivityInstance instance : activities) {
            System.out.println("instance.getActivityId() = " + instance.getActivityId());
            System.out.println("instance.getDurationInMillis() = " + instance.getDurationInMillis());
            System.out.println("instance.getAssignee() = " + instance.getAssignee());
            System.out.println("instance.getProcessInstanceId() = " + instance.getProcessInstanceId());
        }
    }


}
