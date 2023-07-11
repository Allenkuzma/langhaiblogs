package cc.langhai.service;

import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色 service接口
 *
 * @author langhai
 * @date 2022-01-15 16:44
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户的权限
     *
     * @return
     */
    Role getRole();

    /**
     * 获取用户的权限
     *
     * @param userId 用户id
     * @return 角色实体
     */
    Role getRole(Long userId);

    /**
     * 更新用户角色授权
     *
     * @param id
     * @param name
     */
    void updateUserRole(Long id, String name);

    /**
     * 获取指定角色名称的用户
     *
     * @param roleName 角色名称
     * @return
     */
    List<User> getUserByRoleName(String roleName);

    /**
     * 是否为管理员
     *
     * @param user 用户实体类
     * @return 用户实体类
     */
    User determineAdmin(User user);
}
