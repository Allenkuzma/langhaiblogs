package cc.langhai.response;

/**
 * 图片相关枚举类
 *
 * @author langhai
 * @date 2023-01-03 11:39
 */
public enum ImageReturnCode implements ReturnCode{

    IMAGE_UPLOAD_FAIL_00000(500, "图片总数量上限。"),

    IMAGE_PARAM_FAIL_00001(500, "图片参数错误。"),

    IMAGE_ENABLE_FAIL_00002(500, "图片拥有用户已经被禁用，无法获取该资源。"),
    ;

    private Integer code;

    private String message;

    ImageReturnCode(Integer code, String message){
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
