package cc.langhai.service;

import cc.langhai.domain.User;

/**
 * 用户表 service接口
 *
 * @author langhai
 * @date 2022-12-06 22:16
 */
public interface UserService {

    /**
     * 获取用户信息 用户名称查询
     *
     * @return
     */
    User getUserByUsername(String username);

}
