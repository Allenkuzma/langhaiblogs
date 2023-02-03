package cc.langhai.response;

/**
 * 用户访问记录相关返回枚举
 *
 * @author langhai
 * @date 2023-01-19 21:45
 */
public enum VisitReturnCode implements ReturnCode{

    VISIT_DAY_SUCCESS_00000(200, "用户访问记录获取成功。"),

    DEVICE_DAY_SUCCESS_00001(200, "用户访问设备类型获取成功。"),

    VISIT_TODAY_SUCCESS_00002(200, "用户今天访问次数获取成功。"),
    ;

    private Integer code;

    private String message;

    VisitReturnCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
