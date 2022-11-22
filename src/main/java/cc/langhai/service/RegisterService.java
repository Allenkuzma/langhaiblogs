package cc.langhai.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户注册相关 service接口
 *
 * @author langhai
 * @date 2022-11-22 21:13
 */
public interface RegisterService {

    /**
     * 发送验证码邮箱
     *
     * @param email
     * @param request
     */
    void sendEmailCode(String email, HttpServletRequest request);
}
