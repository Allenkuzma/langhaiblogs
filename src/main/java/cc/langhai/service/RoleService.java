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
}
