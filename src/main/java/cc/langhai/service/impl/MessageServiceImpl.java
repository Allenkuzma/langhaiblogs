package cc.langhai.service.impl;

import cc.langhai.config.constant.MessageConstant;
import cc.langhai.domain.Message;
import cc.langhai.exception.BusinessException;
import cc.langhai.mapper.MessageMapper;
import cc.langhai.response.MessageReturnCode;
import cc.langhai.service.MessageService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.IPUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 反馈消息 service接口 实现类
 *
 * @author langhai
 * @date 2022-01-07 16:00
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void save(Message message, HttpServletRequest request) {
        // 判断当前ip地址 当天提交的次数
        String[] nowDayScope = DateUtil.getNowDayScope();
        message.setBeginDate(nowDayScope[0]);
        message.setEndDate(nowDayScope[1]);
        // 获取请求ip地址
        String ip = IPUtil.getIP(request);
        message.setIp(ip);
        // 判断今天提交上限
        List<Message> list = messageMapper.list(message);
        if(list.size() >= MessageConstant.IP_DAY_COUNT_ALL){
            throw new BusinessException(MessageReturnCode.MESSAGE_SAVE_FAIL_00000);
        }
        // 安全机制当天提交的总次数
        List<Message> messageList = messageMapper.sum(message);
        if(messageList.size() >= MessageConstant.IP_DAY_COUNT_SUM_ALL){
            throw new BusinessException(MessageReturnCode.MESSAGE_SAVE_FAIL_00000);
        }
        // 新增反馈信息数据
        message.setAddTime(new Date());
        messageMapper.insertMessage(message);
    }
}
