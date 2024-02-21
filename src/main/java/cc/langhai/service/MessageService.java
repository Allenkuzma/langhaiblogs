package cc.langhai.service;

import cc.langhai.domain.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 反馈消息 service接口
 *
 * @author langhai
 * @date 2022-01-07 16:00
 */
public interface MessageService {

    /**
     * 保存用户反馈消息
     *
     * @param message 反馈消息实体类
     * @param request http请求
     */
    void save(Message message, HttpServletRequest request);

    /**
     * 获取所有留言
     *
     * @return 留言集合
     */
    List<Message> getAllMessage();
}
