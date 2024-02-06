package cc.langhai.service;

import javax.servlet.http.HttpSession;

/**
 * 用户个人空间 service接口
 *
 * @author langhai
 * @date 2022-12-21 22:03
 */
public interface PersonalService {

    /**
     * 更新用户个人信息
     *
     * @param nickname 昵称
     * @param motto 座右铭
     * @param session 会话
     */
    void updateUserInfo(String nickname, String motto, HttpSession session);

}
