package cc.langhai.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
     * @param session
     */
    void register(String username, String password, String nickname, String email, String verifyCodeText, HttpSession session);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param verifyCodeText
     * @param session
     */
    void loginEnter(String username, String password, String verifyCodeText, HttpSession session);

    /**
     * 用户退出
     *
     * @param session
     */
    void loginOut(HttpSession session);
}
