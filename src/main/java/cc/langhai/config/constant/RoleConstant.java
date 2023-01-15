package cc.langhai.config.constant;

import lombok.Data;

/**
 * 角色常量类
 * 这里的数据和MySQL数据库的role表保持一致
 *
 * @author langhai
 * @date 2022-12-24 17:07
 */
@Data
public class RoleConstant {

    /**
     * admin 管理员
     *
     */
    public static final String ADMIN = "admin";

    /**
     * user 普通用户
     *
     */
    public static final String USER = "user";

    /**
     * vip 会员用户
     *
     */
    public static final String VIP = "vip";
}
