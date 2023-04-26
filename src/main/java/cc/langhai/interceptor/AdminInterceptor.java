package cc.langhai.interceptor;

import cc.langhai.domain.User;
import cc.langhai.service.RegisterService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录信息处理拦截器
 *
 * @author langhai
 * @date 2022-11-20 15:25
 */
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private RegisterService registerService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
            User user = (User)request.getSession().getAttribute("user");
            if(ObjectUtil.isNotNull(user)){
                // 判断账户是否被禁用
                if (Boolean.valueOf(false).equals(user.getEnable())){
                    request.getRequestDispatcher("/system/user/userEnablePage").forward(request, response);
                    return false;
                }
                UserContext.set(user);
                return true;
            }

            // 记住我功能实现登录
            if(ObjectUtil.isNull(user)){
                registerService.remember(request, request.getSession());
                user = (User)request.getSession().getAttribute("user");
                if(ObjectUtil.isNotNull(user)){
                    // 判断账户是否被禁用
                    if (Boolean.valueOf(false).equals(user.getEnable())){
                        request.getRequestDispatcher("/system/user/userEnablePage").forward(request, response);
                        return false;
                    }
                    UserContext.set(user);
                    return true;
                }
            }

            response.sendRedirect("/user/loginPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
        return false;
    }
 
    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
         //System.out.println("执行了TestInterceptor的postHandle方法");
    }
 
    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //System.out.println("执行了TestInterceptor的afterCompletion方法");
    }
    
}