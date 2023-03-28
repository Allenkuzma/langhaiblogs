package cc.langhai.netty.domain;

import lombok.Data;

/**
 * 在线客服socket聊天消息对象
 *
 * @author langhai
 * @date 2023-03-23 19:52
 */
@Data
public class OnlineMessage {

    /**
     * 消息发送者id
     */
    private String sendId;

    /**
     * 消息接受者id
     */
    private String acceptId;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 头像
     */
    private String headImg;
}
