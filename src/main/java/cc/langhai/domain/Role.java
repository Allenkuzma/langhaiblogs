package cc.langhai.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 角色 实体类
 *
 * @author langhai
 * @date 2023-01-15 16:25
 */
@Data
@TableName("role")
public class Role {

    private Long id;

    private String name;

    private Date addTime;

    private Date updateTime;
}
