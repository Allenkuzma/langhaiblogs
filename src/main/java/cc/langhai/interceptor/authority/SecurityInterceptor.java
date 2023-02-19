package cc.langhai.interceptor.authority;

import cc.langhai.config.annotation.RequestAuthority;
import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.exception.AuthException;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.SystemReturnCode;
import cc.langhai.service.RoleService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 自定义权限注解 拦截器
 *
 * @author langhai
 * @date 2023-01-11 21:15
 */
public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isAuthority(handler)) {
            throw new AuthException(SystemReturnCode.SYSTEM_AUTH_FAIL_00001);
        }
        return true;
    }

    /**
     * 判断此次请求是否有权限
     *
     * @param handler
     * @return 有权限返回true
     */
    private boolean isAuthority(Object handler) {
        boolean flag = false;
        if (handler instanceof HandlerMethod) {
            // 获得请求的方法
            Method method = ((HandlerMethod) handler).getMethod();
            // 获得该方法上面的注解，如果没有注解，直接返回true,通过
            RequestAuthority annotation = method.getAnnotation(RequestAuthority.class);
            if (annotation != null) {
                // 得到当前登录人的权限,判断请求的权限是否包含在内
                Role role = roleService.getRole();
                if(ObjectUtil.isNull(role)){
                    throw new AuthException(SystemReturnCode.SYSTEM_AUTH_FAIL_00001);
                }
                // 获得注解的值（权限）
                String[] values = annotation.value();
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if(value.equals(role.getName())){
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

}
