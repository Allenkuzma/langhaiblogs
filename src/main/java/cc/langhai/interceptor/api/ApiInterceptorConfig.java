package cc.langhai.interceptor.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * api拦截器配置
 *
 * @author langhai
 * @date 2024-01-16 15:12
 */
@Configuration
public class ApiInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(apiInterceptor()).order(4);
        // 拦截地址配置
        registration.addPathPatterns("/api/token/**");
        registration.addPathPatterns("/api/website/**");
    }
}
