package cc.langhai.mapper;

import cc.langhai.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

}
