package cc.langhai.xss;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * xss filter
 *
 * @author langhai
 * @date 2024-01-22 15:56
 */
@Component
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        String pathInfo = servletRequest.getPathInfo() == null ? "" : servletRequest.getPathInfo();
        // 获取请求url的后两层
        String url = servletRequest.getServletPath() + pathInfo;
        // 获取请求你ip后的全部路径
        String uri = servletRequest.getRequestURI();
        // 注入xss过滤器实例
        XssHttpServletRequestWrapper requestWrapper = new XssHttpServletRequestWrapper(servletRequest);
        // 过滤掉不需要的Xss校验的地址
        if (!uri.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }
        //过滤
        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}