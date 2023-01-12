package cc.langhai.interceptor.visit;

import cc.langhai.domain.Visit;
import cc.langhai.service.VisitService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.IPUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 记录用户访问信息 拦截器
 *
 * @author langhai
 * @date 2023-01-12 10:40
 */
public class VisitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private VisitService visitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取真实ip地址
        String ip = IPUtil.getIP(request);
        // 获取设备类型
        String userAgent = request.getHeader("user-agent");
        // 获取来源
        String referer = request.getHeader("referer");
        // redis获取用户访问信息
        String redisIpAddr = redisTemplate.opsForValue().get("visit:ip:" + ip + " ~ " + DateUtil.getNowDay());
        if(ObjectUtil.isNull(redisIpAddr)){
            redisTemplate.opsForValue().set("visit:ip:" + ip + " ~ " + DateUtil.getNowDay(), userAgent, 10, TimeUnit.MINUTES);
            // 记录到mysql
            Visit visit = new Visit();
            visit.setIpAddr(ip);
            visit.setUserAgent(userAgent);
            visit.setReferer(referer);
            visit.setTime(new Date());
            visitService.save(visit);
        }

        return true;
    }


}
