package cc.langhai.service;

import cc.langhai.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
