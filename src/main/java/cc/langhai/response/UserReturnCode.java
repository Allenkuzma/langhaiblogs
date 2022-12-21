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

    REGISTER_EMAIL_NULL_00005(500, "接收者邮箱地址为空，无法发送邮件。"),

    USER_INFO_EXIST_EMAIL_00006(500, "该邮箱已经被用户注册过了。"),

    USER_NAME_SINGLE_OK_00007(200, "用户名是可以被注册的。"),

    USER_NAME_IS_NULL_00008(500, "用户名不能为空。"),

    USER_NAME_IS_NOT_NULL_00009(500, "用户名已经被使用了。"),

    USER_REGISTER_SUCCESS_00010(200, "用户成功注册。"),

    USER_REGISTER_PARAM_NULL_00011(500, "用户注册参数不能为空。"),

    USER_REGISTER_PARAM_VERIFY_00012(500, "用户注册参数非法。"),

    USER_REGISTER_DAY_COUNT_MAX_00013(500, "用户注册当天数量已经到达上限。"),

    USER_LOGIN_ENTER_YES_00014(200, "用户成功登录。"),

    USER_LOGIN_PARAM_NULL_00015(500, "用户登录参数不能为空。"),

    USER_LOGIN_PARAM_VERIFY_00016(500, "用户登录参数非法。"),

    USER_LOGOUT_YES_00017(200, "用户成功退出。"),

    USER_PERSONAL_UPDATE_USER_INFO_YES_00018(200, "用户个人信息成功更新。"),

    USER_PERSONAL_UPDATE_USER_INFO_PARAM_FAIL_00019(500, "用户个人信息参数校验失败。"),
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
