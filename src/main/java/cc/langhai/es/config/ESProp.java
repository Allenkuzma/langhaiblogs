package cc.langhai.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * es 配置文件
 *
 * @author langhai
 * @date 2023-01-08 15:57
 */
@Data
@Component
@ConfigurationProperties(prefix = "es")
public class ESProp {

    private String ip;

    private String port;

    private String userName;

    private String password;
}
