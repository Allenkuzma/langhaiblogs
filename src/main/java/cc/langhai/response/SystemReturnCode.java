package cc.langhai.response;

/**
 * 系统相关返回枚举
 *
 * @author langhai
 * @date 2022-11-22 21:04
 */
public enum SystemReturnCode implements ReturnCode{

    SYSTEM_UNKNOWN_00000(500, "系统未知错误，请联系管理员。"),

    SYSTEM_AUTH_FAIL_00001(403, "您的权限不足，请联系管理员。")
    ;

    private Integer code;
    private String message;

    SystemReturnCode(Integer code, String message){
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
