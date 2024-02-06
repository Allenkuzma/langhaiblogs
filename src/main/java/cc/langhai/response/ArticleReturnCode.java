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

    ARTICLE_UPDATE_OK_00003(200, "文章更新成功。"),

    ARTICLE_UPDATE_PARAM_FAIL_00004(500, "文章更新参数校验失败。"),

    ARTICLE_DELETE_OK_00005(200, "文章逻辑删除成功"),

    ARTICLE_PARAM_FAIL_00006(500, "文章参数校验失败。"),

    ARTICLE_PERMISSION_FAIL_00007(500, "权限校验不足。"),

    ARTICLE_SORT_FAIL_00008(500, "文章排序错误。"),

    ARTICLE_LABEL_PARAM_NULL_FAIL_00009(500, "标签内容为空。"),

    ARTICLE_PASSWORD_PARAM_LENGTH_FAIL_00010(500, "文章访问密码请设置为六个字符。"),

    ARTICLE_SYSTEM_DELETE_OK_00011(200, "文章真实删除成功。"),

    ARTICLE_SUBMIT_COMMENT_OK_00012(200, "文章进行评论成功。"),

    ARTICLE_SUBMIT_COMMENT_PARAM_FAIL_00013(500, "文章进行评论参数校验失败。"),

    ARTICLE_SUBMIT_COMMENT_USER_FAIL_00014(500, "文章进行评论失败，请登录之后在评论。"),

    ARTICLE_SUBMIT_COMMENT_COUNT_FAIL_00015(500, "文章进行评论失败，评论次数过多。"),
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
