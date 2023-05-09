package cc.langhai.response;

/**
 * 图片服务器相关枚举类
 *
 * @author langhai
 * @date 2023-01-02 23:05
 */
public enum MinioReturnCode implements ReturnCode{

    MINIO_UPLOAD_OK_00000(200, "图片上传成功。"),

    MINIO_UPLOAD_FAIL_00001(500, "图片上传失败。"),

    MINIO_UPLOAD_NULL_00002(500, "图片不能是空的。"),

    MINIO_DELETE_OK_00003(200, "图片删除成功。"),

    MINIO_DELETE_FAIL_00004(500, "图片删除失败。"),

    MINIO_IMAGE_FAIL_00005(500, "图库功能已经被禁用，请联系管理员开通。"),
    ;

    private Integer code;
    private String message;

    MinioReturnCode(Integer code, String message){
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
