package cc.langhai.domain;

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

    private String password;

    private Date addTime;

}
