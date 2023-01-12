package cc.langhai.interceptor.visit;

import cc.langhai.interceptor.authority.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 参观拦截器配置 记录用户访问信息
 *
 * @author langhai
 * @date 2023-01-12 10:40
 */
@Configuration
public class VisitConfig implements WebMvcConfigurer {

    @Bean
    public VisitInterceptor visitInterceptor(){
        return new VisitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(visitInterceptor()).order(3);

        registration.addPathPatterns("/**");
    }
}