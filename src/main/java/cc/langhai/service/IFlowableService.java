package cc.langhai.service;

/**
 * 流程引擎 service接口
 *
 * @author langhai
 * @date 2023-03-28 14:24
 */
public interface IFlowableService {

    /**
     * 启动实例流程
     *
     * @param employee      员工姓名
     * @param nrOfHolidays  请假天数
     * @param description   请假描述
     *//*
    void startProcess(String employee, Integer nrOfHolidays, String description);

    *//**
     * 获取指定工作人的代办任务
     * @param assignee
     *//*
    void taskInstance(String assignee);

    *//**
     * 处理工作
     * @param approved 领导是否审批
     * @param assignee
     *//*
    void handleTask(String approved, String assignee);

    *//**
     * 获取历史审批数据
     * @param assignee
     * @return
     *//*
    void queryHistoryProcess(String assignee);*/
}
