package cc.langhai.service.impl;

import cc.langhai.domain.UserInfo;
import cc.langhai.mapper.UserInfoMapper;
import cc.langhai.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户详情信息表 service接口实现类
 *
 * @author langhai
 * @date 2022-12-06 21:41
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(Long id) {
        UserInfo userInfo = userInfoMapper.getUserInfoById(id);
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoByEmail(String email) {
        UserInfo userInfo = userInfoMapper.getUserInfoByEmail(email);
        return userInfo;
    }

    @Override
    public Integer insertUserInfo(UserInfo userInfo) {
        Integer integer = userInfoMapper.insertUserInfo(userInfo);
        return integer;
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoMapper.updateUserInfo(userInfo);
    }

}
