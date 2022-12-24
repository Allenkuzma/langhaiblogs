package cc.langhai.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器配置
 *
 * @author langhai
 * @date 2022-11-20 17:09
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Bean
    public AdminInterceptor adminInterceptor(){
        return new AdminInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(adminInterceptor());

        registration.addPathPatterns("/user/personalPage");
        registration.addPathPatterns("/user/updateUserInfoPage");
        registration.addPathPatterns("/user/updateUserInfo");
        registration.addPathPatterns("/article/newArticlePage");
        registration.addPathPatterns("/article/issue");
        // 添加不拦截路径
        registration.excludePathPatterns(
                                         "/loginPage",
                                         "/**/*.html",
                                         "/**/*.js",
                                         "/**/*.css",
                                         "/**/*.woff",
                                         "/**/*.ttf",
                                         "/index"
                                         );    
    }
}