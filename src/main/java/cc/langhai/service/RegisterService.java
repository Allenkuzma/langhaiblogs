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
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @param email    邮箱
     * @param verifyCodeText 验证码内容
     * @param session http session
     * @param response http 响应
     */
    void register(String username, String password, String nickname, String email, String verifyCodeText, HttpSession session, HttpServletResponse response);

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
     * @param session http session
     * @param response 用户退出结果
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
     * @param request http 请求
     * @param session http session
     */
    void remember(HttpServletRequest request, HttpSession session);

    /**
     * 判断用户是否登录
     *
     * @param request
     * @return
     */
    JSONObject enter(HttpServletRequest request);

    /**
     * 临时记住我功能 在cookie和redis存储信息用户信息
     *
     * @param username
     * @param response
     */
    void temporaryRemember(String username, HttpServletResponse response);

    /**
     * 用于websocket用户信息校验
     *
     * @param userName      用户账号相关信息
     * @param userPassword  用户秘钥相关信息
     */
    void verifyUserWebSocket(String userName, String userPassword);
}
