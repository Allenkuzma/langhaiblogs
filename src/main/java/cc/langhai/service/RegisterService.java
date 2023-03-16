package cc.langhai.service;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param username          用户账号
     * @param password          用户密码
     * @param verifyCodeText    验证码内容
     * @param session           session会话
     * @param remember          记住我字符内容为 "on" 开启此功能
     * @param response
     */
    void loginEnter(String username, String password, String verifyCodeText,
                    HttpSession session, String remember, HttpServletResponse response);

    /**
     * 用户退出
     *
     * @param session
     * @param response
     */
    void loginOut(HttpSession session, HttpServletResponse response);

    /**
     * 记住我功能 在cookie和redis存储信息用户信息
     *
     * @param username
     * @param response
     */
    void remember(String username, HttpServletResponse response);

    /**
     * 记住我的功能实现 ~ 检验cookie和redis的用户信息是否同步
     *
     * @param request
     * @param session
     */
    void remember(HttpServletRequest request, HttpSession session);

    /**
     * 判断用户是否登录
     *
     * @param request
     * @return
     */
    JSONObject enter(HttpServletRequest request);
}
