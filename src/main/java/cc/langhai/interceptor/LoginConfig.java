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
        registration.addPathPatterns("/user/personPage");
        registration.addPathPatterns("/user/updateUserInfoPage");
        registration.addPathPatterns("/user/updateUserInfo");
        registration.addPathPatterns("/article/newArticlePage");
        registration.addPathPatterns("/article/issue");
        registration.addPathPatterns("/article/articleListPage");
        registration.addPathPatterns("/article/articleList");
        registration.addPathPatterns("/article/articleListCard");
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
        registration.addPathPatterns("/userRole/userRolePage");
        registration.addPathPatterns("/userRole/userRoleList");
        registration.addPathPatterns("/userRole/userRoleUpdatePage");
        registration.addPathPatterns("/userRole/updateUserRole");
        registration.addPathPatterns("/devLog/devLogListPage");
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
        registration.addPathPatterns("/navWebsite/navWebsiteListPage");
        registration.addPathPatterns("/navWebsite/navWebsiteList");
        registration.addPathPatterns("/navWebsite/navWebsiteAddPage");
        registration.addPathPatterns("/navWebsite/addNavWebsite");
        registration.addPathPatterns("/navWebsite/navWebsiteUpdatePage");
        registration.addPathPatterns("/navWebsite/updateNavWebsite");
        registration.addPathPatterns("/navWebsite/deleteNavWebsite");
        registration.addPathPatterns("/netty/customer");
        registration.addPathPatterns("/netty/tourist");
        registration.addPathPatterns("/system/article/systemArticleListPage");
        registration.addPathPatterns("/system/article/systemArticleList");
        registration.addPathPatterns("/system/article/systemArticleShow");
        registration.addPathPatterns("/system/article/systemDeleteArticle");
        registration.addPathPatterns("/system/user/userPage");
        registration.addPathPatterns("/system/user/enable");
        registration.addPathPatterns("/system/user/image");
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