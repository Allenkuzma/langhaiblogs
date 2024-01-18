package cc.langhai.interceptor.api;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api拦截器
 *
 * @author langhai
 * @date 2024-01-16 15:05
 */
public class ApiInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取域名
        String serverName = request.getServerName();
        // TODO 域名校验
        if ("localhost".equals(serverName) || "www.langhai.vip".equals(serverName)) {
            return true;
        }
        return false;
    }
}
