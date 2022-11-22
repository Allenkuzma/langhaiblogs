package cc.langhai.service.impl;

import cc.langhai.config.system.SystemConfig;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.RegisterService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.EmailUtil;
import cc.langhai.utils.IPUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 用户注册相关 service接口实现类
 *
 * @author langhai
 * @date 2022-11-22 21:34
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public void sendEmailCode(String email, HttpServletRequest request) {
        if(StrUtil.isBlank(email)){
            throw new BusinessException(UserReturnCode.REGISTER_EMAIL_NULL_00005);
        }

        String ip = IPUtil.getIP(request);
        //先从redis获得这个ip地址
        String ipCount = redisTemplate.opsForValue().get("email:register:" + ip);
        if(StringUtils.isBlank(ipCount)){
            redisTemplate.opsForValue().set("email:register:" + ip, "1", 24, TimeUnit.HOURS);
        }else {
            Integer integer = Integer.valueOf(ipCount);
            Integer sum = integer + 1;
            if(integer >= systemConfig.getRegisterIPEmailCount()){
                throw new BusinessException(UserReturnCode.REGISTER_IP_EMAIL_COUNT_00004);
            }
            redisTemplate.opsForValue().set("email:register:" + ip, sum.toString(), 24, TimeUnit.HOURS);
        }

        //获取当天注册账号的次数信息
        String nowDay = DateUtil.getNowDay();
        String nowDayCount = redisTemplate.opsForValue().get("email:register:" + nowDay);
        if(StrUtil.isBlank(nowDayCount)){
            redisTemplate.opsForValue().set("email:register:" + nowDay, "1", 24, TimeUnit.HOURS);
        }else {
            Integer integer = Integer.valueOf(nowDayCount);
            if(integer >= systemConfig.getRegisterDayEmailCount()){
                throw new BusinessException(UserReturnCode.REGISTER_DAY_EMAIL_COUNT_00003);
            }
            redisTemplate.opsForValue().set("email:register:" + nowDay, String.valueOf(integer + 1), 24, TimeUnit.HOURS);
        }

        //TODO 检查用户信息邮箱是否被使用


        String send = emailUtil.send(email);
        redisTemplate.opsForValue().set("email:register:" + email, send, 5, TimeUnit.MINUTES);
    }
}
