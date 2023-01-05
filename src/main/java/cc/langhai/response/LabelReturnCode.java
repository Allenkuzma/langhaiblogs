package cc.langhai.response;

/**
 * 标签相关枚举类
 *
 * @author langhai
 * @date 2023-01-02 20:32
 */
public enum LabelReturnCode implements ReturnCode{
    LABEL_COUNT_FAIL_00000(500, "标签数量已到达用户上限。"),

    LABEL_ADD_SUCCESS_00001(200, "标签新增成功。"),

    LABEL_ADD_FAIL_00002(500, "标签新增失败。"),

    LABEL_DELETE_SUCCESS_00003(200, "标签删除成功。"),

    LABEL_DELETE_FAIL_00004(500, "标签删除失败。"),

    LABEL_UPDATE_SUCCESS_00005(200, "标签更新成功。"),

    LABEL_UPDATE_FAIL_00006(500, "标签更新失败。"),

    ;

    private Integer code;
    private String message;

    LabelReturnCode(Integer code, String message){
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
