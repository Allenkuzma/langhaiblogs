package cc.langhai.response;

/**
 * 开发日志记录相关枚举类
 *
 * @author langhai
 * @date 2023-01-20 16:45
 */
public enum DevLogReturnCode implements ReturnCode{

    DEV_LOG_ADD_SUCCESS_00001(200, "开发日志记录新增成功。"),

    DEV_LOG_ADD_FAIL_00002(500, "开发日志记录新增失败。"),

    DEV_LOG_UPDATE_SUCCESS_00003(200, "开发日志记录更新成功。"),

    DEV_LOG_UPDATE_FAIL_00004(500, "开发日志记录更新失败。"),

    DEV_LOG_DELETE_SUCCESS_00005(200, "开发日志记录删除成功。"),

    DEV_LOG_DELETE_FAIL_00006(500, "开发日志记录删除失败。"),

    ;

    private Integer code;

    private String message;

    DevLogReturnCode(Integer code, String message){
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
