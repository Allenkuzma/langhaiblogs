package cc.langhai.config.constant;

/**
 * 防止重复提交常量
 *
 * @author langhai
 * @date 2024-02-26 15:23
 */
public interface IdempotentConstant {
   
   
   String TOKEN = "token";
   
   String PREVENT_DUPLICATION_PREFIX = "PREVENT_DUPLICATION_PREFIX:";

}