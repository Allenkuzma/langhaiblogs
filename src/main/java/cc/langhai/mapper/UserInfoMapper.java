package cc.langhai.mapper;

import cc.langhai.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户详情信息表 Mapper
 *
 * @author langhai
 * @date 2022-12-06 21:33
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 获取用户详情信息 用户id查询
     *
     * @param id
     * @return
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
     * 更新 用户详情信息
     *
     * @param userInfo
     */
    void updateUserInfo(UserInfo userInfo);

}
