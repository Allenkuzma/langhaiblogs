package cc.langhai.interceptor.authority;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 权限拦截器配置
 *
 * @author langhai
 * @date 2023-01-11 21:15
 */
@Configuration
public class AuthorityConfig implements WebMvcConfigurer {

    @Bean
    public SecurityInterceptor securityInterceptor(){
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(securityInterceptor()).order(2);

        registration.addPathPatterns("/links/linksListPage");
        registration.addPathPatterns("/links/linksList");
        registration.addPathPatterns("/links/linksAddPage");
        registration.addPathPatterns("/links/addLinks");
        registration.addPathPatterns("/links/deleteLinks");
        registration.addPathPatterns("/links/linksUpdatePage");
        registration.addPathPatterns("/links/updateLinks");
        registration.addPathPatterns("/userRole/userRolePage");
        registration.addPathPatterns("/userRole/userRoleList");
        registration.addPathPatterns("/userRole/userRoleUpdatePage");
        registration.addPathPatterns("/userRole/updateUserRole");
        registration.addPathPatterns("/devLog/devLogManagePage");
        registration.addPathPatterns("/devLog/devLogAddPage");
        registration.addPathPatterns("/devLog/devLogList");
        registration.addPathPatterns("/devLog/addDevLog");
        registration.addPathPatterns("/devLog/devLogUpdatePage");
        registration.addPathPatterns("/devLog/updateDevLog");
        registration.addPathPatterns("/devLog/deleteDevLog");
        registration.addPathPatterns("/navClassify/navClassifyListPage");
        registration.addPathPatterns("/navClassify/navClassifyList");
        registration.addPathPatterns("/navClassify/navClassifyAddPage");
        registration.addPathPatterns("/navClassify/addNav");
        registration.addPathPatterns("/navClassify/deleteNav");
        registration.addPathPatterns("/navClassify/navClassifyUpdatePage");
        registration.addPathPatterns("/navClassify/updateNav");
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