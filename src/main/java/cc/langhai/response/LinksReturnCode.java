package cc.langhai.response;

/**
 * 友情链接相关枚举类
 *
 * @author langhai
 * @date 2023-01-13 22:26
 */
public enum LinksReturnCode implements ReturnCode{
    LINKS_COUNT_FAIL_00000(500, "友情链接数量已到达管理员上限。"),

    LINKS_ADD_SUCCESS_00001(200, "友情链接新增成功。"),

    LINKS_ADD_FAIL_00002(500, "友情链接新增失败。"),

    LINKS_DELETE_SUCCESS_00003(200, "友情链接删除成功。"),

    LINKS_DELETE_FAIL_00004(500, "友情链接删除失败。"),

    LINKS_UPDATE_SUCCESS_00005(200, "友情链接更新成功。"),

    LINKS_UPDATE_FAIL_00006(500, "友情链接更新失败。"),

    ;

    private Integer code;

    private String message;

    LinksReturnCode(Integer code, String message){
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
