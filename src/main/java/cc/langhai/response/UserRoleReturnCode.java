package cc.langhai.response;

/**
 * 用户授权角色相关枚举类
 *
 * @author langhai
 * @date 2023-01-17 19:39
 */
public enum UserRoleReturnCode implements ReturnCode{

    ROLE_AUTH_FAIL_00000(500, "管理员授权用户失败。"),

    ROLE_AUTH_SUCCESS_00001(200, "管理员授权用户成功。"),
    ;

    private Integer code;
    private String message;

    UserRoleReturnCode(Integer code, String message){
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
