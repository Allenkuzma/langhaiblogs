package cc.langhai.service.impl;

import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.RoleMapper;
import cc.langhai.service.RoleService;
import cc.langhai.utils.UserContext;
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

    @Override
    public Role getRole() {
        Long userId = UserContext.getUserId();
        Role role = roleMapper.getRole(userId);
        return role;
    }
}
