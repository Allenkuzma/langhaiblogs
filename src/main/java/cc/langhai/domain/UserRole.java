package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 用户角色 实体类
 *
 * @author langhai
 * @date 2023-01-15 16:29
 */
@Data
@TableName("user_role")
public class UserRole {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long userId;

    @TableField(value = "add_time", fill = FieldFill.INSERT)
    private Date addTime;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;
}
