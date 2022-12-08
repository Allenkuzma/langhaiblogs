package cc.langhai.service.impl;

import cc.langhai.domain.User;
import cc.langhai.mapper.UserMapper;
import cc.langhai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息表 service接口实现类
 *
 * @author langhai
 * @date 2022-12-06 22:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        return user;
    }

    @Override
    public List<User> getUserListByDay(String date) {
        List<User> userList = userMapper.getUserListByDay(date);
        return userList;
    }

    @Override
    public Integer insertUser(User user) {
        Integer integer = userMapper.insertUser(user);
        return integer;
    }

}
