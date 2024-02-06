package cc.langhai.service;

import cc.langhai.domain.UserInfo;

/**
 * 用户详情信息表 service接口
 *
 * @author langhai
 * @date 2022-12-06 21:41
 */
public interface UserInfoService {


    /**
     * 获取用户详情信息，用户id查询。
     *
     * @param id 用户id
     * @return 用户详情信息
     */
    UserInfo getUserInfoById(Long id);

    /**
     * 获取用户详情信息 用户邮箱查询
     *
     * @param email
     * @return
     */
    UserInfo getUserInfoByEmail(String email);

    /**
     * 新增一条 用户详情信息
     *
     * @param userInfo
     * @return
     */
    Integer insertUserInfo(UserInfo userInfo);

    /**
     * 更新用户详情信息
     *
     * @param userInfo 用户详情信息实体类
     */
    void updateUserInfo(UserInfo userInfo);
}
