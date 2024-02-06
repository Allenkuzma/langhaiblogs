package cc.langhai.response;

/**
 * 公共导航网站相关枚举类
 *
 * @author langhai
 * @date 2023-02-26 13:14
 */
public enum NavWebsiteReturnCode implements ReturnCode{
    NAV_WEBSITE_CLASSIFY_COUNT_FAIL_00000(500, "公共导航网站数量已到达每一个分类的上限。"),

    NAV_WEBSITE_ADD_SUCCESS_00001(200, "公共导航网站新增成功。"),

    NAV_WEBSITE_ADD_FAIL_00002(500, "公共导航网站新增失败。"),

    NAV_WEBSITE_DELETE_SUCCESS_00003(200, "公共导航网站删除成功。"),

    NAV_WEBSITE_DELETE_FAIL_00004(500, "公共导航网站删除失败。"),

    NAV_WEBSITE_UPDATE_SUCCESS_00005(200, "公共导航网站更新成功。"),

    NAV_WEBSITE_UPDATE_FAIL_00006(500, "公共导航网站更新失败。"),

    ;

    private Integer code;

    private String message;

    NavWebsiteReturnCode(Integer code, String message){
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
