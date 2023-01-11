package cc.langhai.config.annotation;

import java.lang.annotation.*;

/**
 * 自定义权限注解
 *
 * @author langhai
 * @date 2023-01-11 21:10
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestAuthority {

    String[] value();
}
