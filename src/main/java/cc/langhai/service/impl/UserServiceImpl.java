package cc.langhai.service.impl;

import cc.langhai.domain.User;
import cc.langhai.exception.BusinessException;
import cc.langhai.listener.LonginUserSessionConfig;
import cc.langhai.mapper.UserMapper;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.UserService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
    public List<User> getUserList(String username, String nickname) {
        List<User> list = userMapper.getUserList(username, nickname);

        return list;
    }

    @Override
    public void enable(Long id, Boolean enable) {
        User user = userMapper.getUserById(id);
        if(ObjectUtil.isNull(user)){
            throw new BusinessException(UserReturnCode.USER_UPDATE_ENABLE_FAIL_00025);
        }

        user.setEnable(enable);
        userMapper.updateById(user);

        // 判断其他地方是否登录
        // 删除当前登录用户已绑定的HttpSession map中的remove方法返回删除value值
        HttpSession sessionMap = LonginUserSessionConfig.USER_SESSION.remove(user.getUsername());

        if (sessionMap != null){
            // 删除已登录的sessionId绑定的用户
            sessionMap.removeAttribute("user");

            // 当前session销毁时删除当前session绑定的用户信息
            // 同时删除当前session绑定用户的HttpSession
            LonginUserSessionConfig.SESSION_ID_USER.remove(sessionMap.getId());
        }
    }

    @Override
    public void image(Long id, Boolean image) {
        User user = userMapper.getUserById(id);
        if(ObjectUtil.isNull(user)){
            throw new BusinessException(UserReturnCode.USER_UPDATE_IMAGE_FAIL_00027);
        }

        user.setImage(image);
        userMapper.updateById(user);

        // 判断其他地方是否登录
        // 删除当前登录用户已绑定的HttpSession map中的remove方法返回删除value值
        HttpSession sessionMap = LonginUserSessionConfig.USER_SESSION.remove(user.getUsername());

        if (sessionMap != null){
            // 删除已登录的sessionId绑定的用户
            sessionMap.removeAttribute("user");

            // 当前session销毁时删除当前session绑定的用户信息
            // 同时删除当前session绑定用户的HttpSession
            LonginUserSessionConfig.SESSION_ID_USER.remove(sessionMap.getId());
        }
    }

}
