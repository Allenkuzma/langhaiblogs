package cc.langhai.interceptor.api;

import cn.hutool.core.util.StrUtil;
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
        String referer = request.getHeader("referer");
        if (StrUtil.isNotBlank(referer)) {
            boolean httpsBoolean = referer.startsWith("https://www.langhai.net");
            boolean httpBoolean = referer.startsWith("http://www.langhai.net");
            if (httpsBoolean || httpBoolean) {
                return true;
            }
        } else {
            String serverName = request.getServerName();
            if ("localhost".equals(serverName)) {
                return true;
            }
        }

        return false;
    }
}
