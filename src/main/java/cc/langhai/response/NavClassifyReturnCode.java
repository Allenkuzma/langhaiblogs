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

    NAV_CLASSIFY_DELETE_SUCCESS_00005(200, "公共导航分类删除成功。"),

    NAV_CLASSIFY_DELETE_FAIL_00006(500, "公共导航分类删除失败。"),

    NAV_CLASSIFY_DELETE_FAIL_EXIST_WEBSITE_00007(500, "公共导航分类存在导航网站，删除失败。"),

    NAV_CLASSIFY_UPDATE_SUCCESS_00008(200, "公共导航分类更新成功。"),

    NAV_CLASSIFY_UPDATE_FAIL_00009(500, "公共导航分类更新失败。"),

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
