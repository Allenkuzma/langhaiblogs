package cc.langhai.netty.config;

import cc.langhai.domain.User;
import cc.langhai.netty.handler.OnlineWebSocketHandler;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * session超时,移除websocket对应的channel。
 *
 * @author langhai
 * @date 2023-03-23 19:23
 */
@Slf4j
@WebListener
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.info("sessionCreated sessionId={}", httpSessionEvent.getSession().getId());
        MySessionContext.AddSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        User user = (User) session.getAttribute("user");
        // 销毁时从websocket channel中移除
        if (ObjectUtil.isNotNull(user)) {
            ChannelId channelId = OnlineWebSocketHandler.userMap.get(user.getUsername());
            if (channelId != null) {
                // 移除了私聊的channel对象, 群聊的还未移除
                OnlineWebSocketHandler.userMap.remove(user.getUsername());
                OnlineWebSocketHandler.channelGroup.remove(channelId);
                log.info("session timeout,remove channel, userName={}", user.getUsername());
            }
        }
        MySessionContext.DelSession(session);
        log.info("session destroyed  .... ");
    }

    public static class MySessionContext {

        private static HashMap myMap = new HashMap();

        public static synchronized void AddSession(HttpSession session) {
            if (session != null) {
                myMap.put(session.getId(), session);
            }
        }

        public static synchronized void DelSession(HttpSession session) {
            if (session != null) {
                myMap.remove(session.getId());
            }
        }

        public static synchronized HttpSession getSession(String sessionId) {
            if (StrUtil.isNotBlank(sessionId)) {
                return null;
            }
            return (HttpSession) myMap.get(sessionId);
        }
    }
}
