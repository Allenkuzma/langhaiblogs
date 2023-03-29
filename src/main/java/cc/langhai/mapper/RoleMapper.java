package cc.langhai.mapper;

import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @author langhai
 * @date 2023-01-15 16:34
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取用户的权限
     *
     * @param userId
     * @return
     */
    Role getRole(Long userId);

    /**
     * 获取指定角色名称的用户
     *
     * @param roleName 角色名称
     * @return
     */
    List<User> getUserByRoleName(String roleName);

}
