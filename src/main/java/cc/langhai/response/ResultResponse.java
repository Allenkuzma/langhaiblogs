package cc.langhai.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一封装结果集
 *
 * @author langhai
 * @date 2022-11-22 20:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse<T> {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果集
     */
    private T result;

    /**
     * 响应成功 不带响应数据
     *
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> success(){
        return new ResultResponse<>(200, "响应成功", null);
    }

    /**
     * 响应成功 不带响应数据
     *
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> success(ReturnCode returnCode){
        return new ResultResponse<>(returnCode.getCode(), returnCode.getMessage(), null);
    }

    /**
     * 响应成功 带响应数据
     *
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> success(ReturnCode returnCode, T object){
        return new ResultResponse<>(returnCode.getCode(), returnCode.getMessage(), object);
    }

    /**
     * 响应失败 不带响应数据
     *
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> fail(){
        return new ResultResponse<>(500, "响应失败", null);
    }

    /**
     * 响应失败 不带响应数据
     *
     * @return
     * @param <T>
     */
    public static <T> ResultResponse<T> fail(ReturnCode returnCode){
        return new ResultResponse<>(returnCode.getCode(), returnCode.getMessage(), null);
    }
}
