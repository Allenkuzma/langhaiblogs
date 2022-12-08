package cc.langhai.service.impl;

import cc.langhai.config.system.SystemConfig;
import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.RegisterService;
import cc.langhai.service.UserInfoService;
import cc.langhai.service.UserService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.EmailUtil;
import cc.langhai.utils.IPUtil;
import cc.langhai.utils.StringUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
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
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;

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

        // 检查用户信息邮箱是否被使用
        UserInfo userInfo = userInfoService.getUserInfoByEmail(email);
        if(ObjectUtil.isNotNull(userInfo)){
            throw new BusinessException(UserReturnCode.USER_INFO_EXIST_EMAIL_00006);
        }

        String send = emailUtil.send(email);
        redisTemplate.opsForValue().set("email:register:" + email, send, 5, TimeUnit.MINUTES);
    }

    @Override
    public void verifyUsername(String username) {
        if(StrUtil.isBlank(username)){
            throw new BusinessException(UserReturnCode.USER_NAME_IS_NULL_00008);
        }

        User user = userService.getUserByUsername(username);
        if(ObjectUtil.isNotNull(user)){
            throw new BusinessException(UserReturnCode.USER_NAME_IS_NOT_NULL_00009);
        }
    }

    @Override
    @Transactional
    public void register(String username, String password, String nickname, String email, String verifyCodeText) {

        if(StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname)
                || StrUtil.isBlank(email) || StrUtil.isBlank(verifyCodeText)){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_NULL_00011);
        }

        // 对用户名进行合法判断 用户名（3到8位的数字和字母组合）
        if(username.length() > 8 || username.length() < 3){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_00012);
        }

        if(!StringUtil.isAlphaNumeric(username)){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_00012);
        }

        this.verifyUsername(username);

        // 对用户密码进行合法校验 用户密码（6到18位的字符组合）
        if(password.length() < 6 || password.length() > 18){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_00012);
        }
        // 构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, systemConfig.getSecret().getBytes());
        // 加密为16进制表示
        String encryptHexPassword = aes.encryptHex(password);

        // 对昵称进行合法校验 昵称（1到12位的字符组合）
        if(nickname.length() < 1 || nickname.length() > 12){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_00012);
        }

        // 对邮箱地址合法校验
        // 检查用户信息邮箱是否被使用
        UserInfo userInfo = userInfoService.getUserInfoByEmail(email);
        if(ObjectUtil.isNotNull(userInfo)){
            throw new BusinessException(UserReturnCode.USER_INFO_EXIST_EMAIL_00006);
        }

        // 对验证码校验
        String redisVerifyCodeText = redisTemplate.opsForValue().get("email:register:" + email);
        if(StringUtils.isBlank(redisVerifyCodeText) || !verifyCodeText.equals(redisVerifyCodeText)){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_00012);
        }

        // 当天注册机会限制 配置文件中registerDayUserCount可以配置
        List<User> userListByDay = userService.getUserListByDay(DateUtil.getNowDay());
        if(userListByDay.size() > systemConfig.getRegisterDayUserCount()){
            throw new BusinessException(UserReturnCode.USER_REGISTER_DAY_COUNT_MAX_00013);
        }

        // 用户信息
        User user = new User();
        user.setUsername(username);
        user.setPassword(encryptHexPassword);
        user.setNickname(nickname);
        user.setAddTime(new Date());
        userService.insertUser(user);

        // 用户详情信息
        UserInfo userInfoSave = new UserInfo();
        userInfoSave.setId(user.getId());
        userInfoSave.setEmail(email);
        userInfoService.insertUserInfo(userInfoSave);

    }
}
