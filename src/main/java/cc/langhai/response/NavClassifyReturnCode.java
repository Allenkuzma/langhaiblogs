package cc.langhai.response;

/**
 * 公共导航分类相关枚举类
 *
 * @author langhai
 * @date 2023-01-30 13:03
 */
public enum NavClassifyReturnCode implements ReturnCode{
    NAV_CLASSIFY_COUNT_FAIL_00000(500, "公共导航分类数量已到达管理员上限。"),

    NAV_CLASSIFY_ADD_SUCCESS_00001(200, "公共导航分类新增成功。"),

    NAV_CLASSIFY_ADD_FAIL_00002(500, "公共导航分类新增失败。"),

    NAV_CLASSIFY_NAME_EXISTENCE_00003(500, "公共导航分类名字已经存在。"),

    NAV_CLASSIFY_TAG_NAME_EXISTENCE_00004(500, "公共导航分类标签名字已经存在。"),
    ;

    private Integer code;

    private String message;

    NavClassifyReturnCode(Integer code, String message){
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
