package cc.langhai.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 登录session监听器 限制一个账号只能一处登录
 *
 * @author langhai
 * @date 2023-01-12 14:53
 */
@WebListener
public class LoginListener implements HttpSessionListener {
     
    /**
     * 实现HttpSessionListener接口监听 监听session的创建事件
     *
     */
    @Override
    public void sessionCreated(HttpSessionEvent se){
        String sessionId = se.getSession().getId();
        System.out.println("创建sessionId = " + sessionId);
    }
 
    /**
     * 实现HttpSessionListener接口监听 监听session的销毁事件
     *
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se){
        String sessionId = se.getSession().getId();
        System.out.println("销毁sessionId = " + sessionId);

        // 当前session销毁时删除当前session绑定的用户信息
        // 同时删除当前session绑定用户的HttpSession
        LonginUserSessionConfig.USER_SESSION.remove(LonginUserSessionConfig.SESSION_ID_USER.remove(sessionId));

    }
 
     
 
}