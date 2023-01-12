package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户访问实体表
 *
 * @author langhai
 * @date 2022-01-12 11:03
 */
@Data
@TableName("visit")
public class Visit {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("ip_addr")
    private String ipAddr;

    @TableField("time")
    private Date time;

    @TableField("referer")
    private String referer;

    @TableField("user_agent")
    private String userAgent;
}
