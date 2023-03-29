package cc.langhai.service.impl;

import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.domain.UserRole;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.RoleMapper;
import cc.langhai.response.UserRoleReturnCode;
import cc.langhai.service.RoleService;
import cc.langhai.service.UserRoleService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 角色 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-15 16:46
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Role getRole() {
        Long userId = UserContext.getUserId();
        Role role = roleMapper.getRole(userId);
        return role;
    }

    @Override
    public Role getRole(Long userId) {
        Role role = roleMapper.getRole(userId);
        return role;
    }

    @Override
    public void updateUserRole(Long id, String name) {
        // 需要变更到的角色
        Role role = this.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, name));
        if(ObjectUtil.isNull(role)){
            throw new BusinessException(UserRoleReturnCode.ROLE_AUTH_FAIL_00000);
        }

        // 查询现在的角色
        Role roleOld = this.getRole(id);
        if(role.equals(roleOld)){
            return;
        }

        UserRole userRole = userRoleService.getOne(Wrappers.<UserRole>lambdaQuery()
                .eq(UserRole::getUserId, id)
                .eq(UserRole::getRoleId, roleOld.getId()));
        if(ObjectUtil.isNull(userRole)){
            throw new BusinessException(UserRoleReturnCode.ROLE_AUTH_FAIL_00000);
        }

        userRole.setRoleId(role.getId());
        userRoleService.updateById(userRole);
    }

    @Override
    public List<User> getUserByRoleName(String roleName) {
        List<User> userList = roleMapper.getUserByRoleName(roleName);

        return userList;
    }
}
