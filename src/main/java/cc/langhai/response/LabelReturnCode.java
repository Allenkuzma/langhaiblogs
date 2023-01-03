package cc.langhai.response;

/**
 * 标签相关枚举类
 *
 * @author langhai
 * @date 2023-01-02 20:32
 */
public enum LabelReturnCode implements ReturnCode{
    LABEL_COUNT_FAIL_00000(500, "标签数量已到达用户上限。"),

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
