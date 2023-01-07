package cc.langhai.response;

/**
 * 用户反馈消息相关枚举类
 *
 * @author langhai
 * @date 2022-01-07 16:03
 */
public enum MessageReturnCode implements ReturnCode{

    MESSAGE_SAVE_FAIL_00000(500, "用户反馈信息存在安全隐患，禁止提交。"),

    MESSAGE_SAVE_SUCCESS_00001(200, "用户反馈信息提交成功。"),

    ;

    private Integer code;
    private String message;

    MessageReturnCode(Integer code, String message){
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
