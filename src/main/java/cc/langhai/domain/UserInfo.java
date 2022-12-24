package cc.langhai.domain;

import lombok.Data;

import java.util.Date;

/**
 * 用户详情信息表
 *
 * @author langhai
 * @date 2022-12-06 21:16
 */
@Data
public class UserInfo {

    private Long id;

    private String email;

    private String motto;

    /**
     * 用户注册天数
     *
     */
    private Long day;

    private Date updateTime;

}
