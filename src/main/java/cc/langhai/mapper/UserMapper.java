package cc.langhai.mapper;

import cc.langhai.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户实体表 Mapper
 *
 * @author langhai
 * @date 2022-12-06 22:11
 */
@Mapper
public interface UserMapper {

    /**
     * 获取用户信息 用户名称查询
     *
     * @return
     */
    User getUserByUsername(String username);
}
