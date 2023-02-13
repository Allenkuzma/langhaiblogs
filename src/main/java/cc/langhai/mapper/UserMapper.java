package cc.langhai.mapper;

import cc.langhai.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户实体表 Mapper
 *
 * @author langhai
 * @date 2022-12-06 22:11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户信息 用户名称查询
     *
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 查询用户信息 注册时间为当天
     *
     * @param date
     * @return
     */
    List<User> getUserListByDay(String date);

    /**
     * 新增一个用户信息
     *
     * @return
     */
    Integer insertUser(User user);

    /**
     * 获取用户信息 用户名称和用户密码查询
     *
     * @return
     */
    User getUserByUsernameAndPassword(String username, String password);

    /**
     * 获取用户信息 id查询
     *
     * @return
     */
    User getUserById(Long id);

    /**
     * 更新用户信息
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 获取所有用户信息
     *
     * @param username
     * @param nickname
     * @return
     */
    List<User> getUserList(String username, String nickname);

}
