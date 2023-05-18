package cc.langhai.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 反馈消息实体类
 *
 * @author langhai
 * @date 2023-01-07 15:49
 */
@Data
public class Message {

    private Long id;

    @NotBlank(message = "反馈人名字不能为空")
    private String name;

    @NotBlank(message = "反馈人邮箱不能为空")
    private String email;

    @NotBlank(message = "反馈人消息内容不能为空")
    private String message;

    private String ip;

    private Date addTime;

    /**
     * 查询数据库使用
     */
    private String beginDate;

    /**
     * 查询数据库使用
     */
    private String endDate;
}
