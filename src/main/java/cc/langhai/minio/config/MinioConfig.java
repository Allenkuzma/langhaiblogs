package cc.langhai.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
/**
 * minio 核心配置类
 *
 * @author langhai
 * @date 2023-01-02 22:19
 */
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {
 
    @Autowired
    private MinioProp minioProp;
 
    /**
     * 获取 MinioClient
     *
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioProp.getEndpoint(), minioProp.getAccessKey(), minioProp.getSecretKey());
    }
}
 