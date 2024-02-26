package cc.langhai.config.annotation;

import java.lang.annotation.*;

/**
 * 自定义防止重复提交注解
 *
 * @author langhai
 * @date 2024-02-26 15:09
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {
   
   /**
    * 防重复操作限时标记数值（存储redis限时标记数值）
    */
   String value() default "value" ;
   
   /**
    * 防重复操作过期时间（借助redis实现限时控制）
    */
   long expireSeconds() default 10;
}