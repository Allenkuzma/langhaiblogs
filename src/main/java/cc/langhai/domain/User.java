package cc.langhai.domain;

import cc.langhai.config.constant.RoleConstant;
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
public class User {

    private Long id;

    private String username;

    private String nickname;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date addTime;

    /**
     * 用来展示新增时间 yyyy-MM-dd HH:mm:ss
     *
     */
    private String addTimeShow;

    private Date updateTime;

    private String role;

    /**
     * 是否为管理员
     */
    private boolean isAdmin;

}
