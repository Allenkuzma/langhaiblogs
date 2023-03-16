package cc.langhai.config.constant;

import cc.langhai.domain.Article;
import lombok.Data;

import java.util.TreeSet;

/**
 * 用户常量类
 *
 * @author langhai
 * @date 2023-03-16 14:02
 */
@Data
public class UserConstant {

    /**
     * 用户登录5分钟内校验参数错误次数
     *
     */
    public static final Integer USER_LOGIN_ERROR_COUNT = 5;

}
