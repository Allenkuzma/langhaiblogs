package cc.langhai.config.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mysql链接信息
 *
 * @author langhai
 * @date 2023-01-27 15:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class MySQLConfig {

    private String url;

    private String username;

    private String password;

    private String driverClassName;
}
