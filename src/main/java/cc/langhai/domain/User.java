package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 *
 * @author langhai
 * @date 2022-11-20 17:01
 */
@Data
@TableName("user")
public class User {

    private Long id;

    private String username;

    private String nickname;

    private String password;

    private Boolean enable;

    private Boolean image;

    @TableField(value = "add_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    /**
     * 用来展示新增时间 yyyy-MM-dd HH:mm:ss
     */
    @TableField(exist = false)
    private String addTimeShow;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private String role;

    /**
     * 是否为管理员
     */
    @TableField(exist = false)
    private boolean isAdmin;

}
