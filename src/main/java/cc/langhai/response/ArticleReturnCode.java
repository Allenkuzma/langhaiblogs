package cc.langhai.response;

/**
 * 文章相关枚举类
 *
 * @author langhai
 * @date 2022-12-24 16:17
 */
public enum ArticleReturnCode implements ReturnCode{
    ARTICLE_ISSUE_OK_00000(200, "文章发布成功。"),

    ARTICLE_ISSUE_PARAM_FAIL_00001(500, "文章发布参数校验失败。"),

    ARTICLE_ISSUE_COUNT_DAY_FAIL_00002(500, "文章发布当天次数上限。"),
    ;

    private Integer code;
    private String message;

    ArticleReturnCode(Integer code, String message){
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
