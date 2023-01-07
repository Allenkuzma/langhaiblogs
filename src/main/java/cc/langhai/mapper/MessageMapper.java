package cc.langhai.mapper;

import cc.langhai.domain.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户反馈消息 Mapper
 *
 * @author langhai
 * @date 2022-01-07 16:05
 */
@Mapper
public interface MessageMapper {


    /**
     * 查询ip地址 当天提交记录
     *
     * @param message
     * @return
     */
    List<Message> list(Message message);

    /**
     * 当天提交记录总次数
     *
     * @param message
     * @return
     */
    List<Message> sum(Message message);

    /**
     * 新增反馈消息
     *
     */
    void insertMessage(Message message);
}
