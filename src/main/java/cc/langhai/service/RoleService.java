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
     * @return
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
}
