package cc.langhai.utils;

import cc.langhai.domain.User;

/**
 * 本地线程变量用户
 *
 * @author langhai
 * @date 2022-11-20 17:12
 */
public class UserContext {

    private static ThreadLocal<User> userThread = new ThreadLocal<>();

    public static void set(User user) {
        userThread.set(user);
    }

    public static User get() {
        return userThread.get();
    }

    /**
     * 获取当前登录用户的ID 未登录返回null
     *
     * @return
     */
    public static Long getUserId() {
        User user = get();
        if (user != null && user.getId() != null) {
            return user.getId();
        }
        return null;
    }

    /**
     * 防止内存泄漏
     */
    public static void remove() {
        userThread.remove();
    }
}
