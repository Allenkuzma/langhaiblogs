package cc.langhai.response;

/**
 * 文章评论相关枚举类
 *
 * @author langhai
 * @date 2023-07-11 14:42
 */
public enum ArticleCommentReturnCode implements ReturnCode{
    ARTICLE_COMMENT_DELETE_OK_00001(200, "文章评论真实删除成功。"),

    ARTICLE_COMMENT_DELETE_FAIL_00002(500, "文章评论真实删除失败。"),
    ;

    private Integer code;

    private String message;

    ArticleCommentReturnCode(Integer code, String message){
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
