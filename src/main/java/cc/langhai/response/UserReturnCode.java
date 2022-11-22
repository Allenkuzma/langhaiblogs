package cc.langhai.response;

/**
 * 用户相关返回枚举
 *
 * @author langhai
 * @date 2022-11-22 20:28
 */
public enum UserReturnCode implements ReturnCode{

    EMAIL_CODE_00000(200, "邮件验证码发送成功，请注意查收。"),

    EMAIL_CODE_00001(500, "邮件验证码发送失败，请联系管理员。"),

    REGISTER_DAY_EMAIL_COUNT_00003(500, "当天邮件发送次数已达到上限，无法发送邮件。"),

    REGISTER_IP_EMAIL_COUNT_00004(500, "当前IP24小时之内被暂时封禁，无法发送邮件。"),

    REGISTER_EMAIL_NULL_00005(500, "接受者邮箱地址为空，无法发送邮件。"),
    ;

    private Integer code;
    private String message;

    UserReturnCode(Integer code, String message){
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
