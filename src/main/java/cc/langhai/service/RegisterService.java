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

    /**
     * 校验用户名的唯一性
     *
     * @param username
     */
    void verifyUsername(String username);

    /**
     * 用户注册
     *
     * @param username
     * @param password
     * @param nickname
     * @param email
     * @param verifyCodeText
     */
    void register(String username, String password, String nickname, String email, String verifyCodeText);

}
