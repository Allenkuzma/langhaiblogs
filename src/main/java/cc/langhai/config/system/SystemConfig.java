package cc.langhai.config.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置参数
 *
 * @author langhai
 * @date 2022-11-22 22:09
 */
@Data
@Component
@ConfigurationProperties(prefix = "langhai.blogs")
public class SystemConfig {

    /**
     * 允许系统当天发送邮箱验证码最大次数
     */
    private Integer registerDayEmailCount;

    /**
     * 允许该ip24小时之内发送邮箱验证码次数
     */
    private Integer registerIPEmailCount;

}
