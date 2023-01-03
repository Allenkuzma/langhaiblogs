package cc.langhai.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
/**
 * minio 配置文件
 *
 * @author langhai
 * @date 2023-01-02 22:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {

    /**
     * 连接url
     *
     */
    private String endpoint;

    /**
     * 用户名
     *
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;
}
 