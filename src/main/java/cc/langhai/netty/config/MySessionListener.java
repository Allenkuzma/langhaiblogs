package cc.langhai.netty.config;

import cc.langhai.netty.handler.WebSocketHandler;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * session超时, 移除 websocket对应的channel。
 *
 * @author langhai
 * @date 2023-03-23 19:23
 */
@Slf4j
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.info("sessionCreated sessionId={}", httpSessionEvent.getSession().getId());
        MySessionContext.AddSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Integer userId = session.getAttribute("userId") == null ? null : Integer.parseInt(session.getAttribute("userId").toString());
        // 销毁时从websocket channel中移除
        if (userId != null) {
            ChannelId channelId = WebSocketHandler.userMap.get(userId);
            if (channelId != null) {
                // 移除了私聊的channel对象, 群聊的还未移除
                WebSocketHandler.userMap.remove(userId);
                WebSocketHandler.channelGroup.remove(channelId);
                log.info("session timeout,remove channel, userId={}", userId);
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

        public static synchronized HttpSession getSession(String session_id) {
            if (session_id == null) {
                return null;
            }
            return (HttpSession) myMap.get(session_id);
        }
    }
}
