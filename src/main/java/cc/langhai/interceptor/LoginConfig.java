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
        InterceptorRegistration registration = registry.addInterceptor(adminInterceptor()).order(1);

        registration.addPathPatterns("/user/personalPage");
        registration.addPathPatterns("/user/updateUserInfoPage");
        registration.addPathPatterns("/user/updateUserInfo");
        registration.addPathPatterns("/article/newArticlePage");
        registration.addPathPatterns("/article/issue");
        registration.addPathPatterns("/article/articleListPage");
        registration.addPathPatterns("/article/articleList");
        registration.addPathPatterns("/article/updateArticlePage");
        registration.addPathPatterns("/article/updateArticle");
        registration.addPathPatterns("/article/deleteArticle");
        registration.addPathPatterns("/minio/upload");
        registration.addPathPatterns("/minio/upload/wangEditor");
        registration.addPathPatterns("/minio/delete");
        registration.addPathPatterns("/image/imagePage");
        registration.addPathPatterns("/image/imageAddPage");
        registration.addPathPatterns("/label/labelPage");
        registration.addPathPatterns("/label/labelList");
        registration.addPathPatterns("/label/addLabel");
        registration.addPathPatterns("/label/deleteLabel");
        registration.addPathPatterns("/label/updateLabel");
        registration.addPathPatterns("/label/labelAddPage");
        registration.addPathPatterns("/label/labelUpdatePage");
        registration.addPathPatterns("/label/articleLabelPage");
        registration.addPathPatterns("/links/linksListPage");
        registration.addPathPatterns("/links/linksList");
        registration.addPathPatterns("/links/linksAddPage");
        registration.addPathPatterns("/links/addLinks");
        registration.addPathPatterns("/links/deleteLinks");
        registration.addPathPatterns("/links/linksUpdatePage");
        registration.addPathPatterns("/links/updateLinks");
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