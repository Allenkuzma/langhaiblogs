package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 开发日志实体表
 *
 * @author langhai
 * @date 2022-01-16 18:32
 */
@Data
@TableName("dev_log")
public class DevLog {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Date addTime;

    private Date updateTime;
}
