package cc.langhai.service.impl;

import cc.langhai.domain.User;
import cc.langhai.mapper.UserMapper;
import cc.langhai.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = userMapper.getUserByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        User user = userMapper.getUserById(id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public List<User> getUserList() {
        List<User> list = userMapper.getUserList();
        return list;
    }

}
