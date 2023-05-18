package cc.langhai.config.constant;

import lombok.Data;

/**
 * 反馈消息常量类
 *
 * @author langhai
 * @date 2023-01-07 15:57
 */
@Data
public class MessageConstant {

    /**
     * 当天提交次数限制 ip地址限制
     */
    public static final Integer IP_DAY_COUNT_ALL = 5;

    /**
     * 当天提交次数限制 总次数限制
     */
    public static final Integer IP_DAY_COUNT_SUM_ALL = 50;

}
