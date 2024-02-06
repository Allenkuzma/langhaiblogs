package cc.langhai.response;

/**
 * netty相关返回枚举
 *
 * @author langhai
 * @date 2023-03-29 10:52
 */
public enum NettyCode implements ReturnCode{

    USER_INFO_VERIFY_PARAM_NULL_FAIL_00000(500, "用户信息校验参数为空值。"),

    USER_INFO_VERIFY_PARAM_FAIL_00000(500, "用户信息校验失败。"),
    ;

    private Integer code;

    private String message;

    NettyCode(Integer code, String message){
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
