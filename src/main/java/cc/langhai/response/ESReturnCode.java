package cc.langhai.response;

/**
 * es搜索引擎相关枚举类
 *
 * @author langhai
 * @date 2023-01-09 10:42
 */
public enum ESReturnCode implements ReturnCode{

    ES_DOC_DELETE_FAIL_00000(500, "数据文档删除失败！！！"),

    ES_DOC_UPDATE_FAIL_00001(500, "数据文档新增/修改失败！！！")

    ;

    private Integer code;

    private String message;

    ESReturnCode(Integer code, String message){
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
