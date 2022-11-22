package cc.langhai.response;

/**
 * 提示信息 公共接口
 *
 * @author langhai
 * @date 2022-11-22 20:36
 */
public interface ReturnCode {

    /**
     * 获取状态码
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取详细消息
     *
     * @return
     */
    String getMessage();
}
