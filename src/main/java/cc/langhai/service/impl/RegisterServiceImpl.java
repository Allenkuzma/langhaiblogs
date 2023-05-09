package cc.langhai.service.impl;

import cc.langhai.config.constant.RoleConstant;
import cc.langhai.config.constant.UserConstant;
import cc.langhai.config.system.SystemConfig;
import cc.langhai.domain.Role;
import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.domain.UserRole;
import cc.langhai.exception.BusinessException;
import cc.langhai.listener.LonginUserSessionConfig;
import cc.langhai.response.NettyCode;
import cc.langhai.response.SystemReturnCode;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.*;
import cc.langhai.utils.*;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void sendEmailCode(String email, HttpServletRequest request) {
        if(StrUtil.isBlank(email)){
            throw new BusinessException(UserReturnCode.REGISTER_EMAIL_NULL_00005);
        }

        String ip = IPUtil.getIP(request);
        // 先从redis获得这个ip地址
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

        // 获取当天注册账号的次数信息
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
    @Transactional(rollbackFor = Exception.class)
    public void register(String username, String password, String nickname, String email, String verifyCodeText, HttpSession session, HttpServletResponse response) {

        if(StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(nickname)
                || StrUtil.isBlank(email) || StrUtil.isBlank(verifyCodeText)){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_NULL_00011);
        }

        // 对用户名进行合法判断 用户名（3到8位的数字和字母组合）
        if(username.length() > 8 || username.length() < 3){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_LENGTH_00022);
        }

        if(!StringUtil.isAlphaNumeric(username)){
            throw new BusinessException(UserReturnCode.USER_REGISTER_PARAM_VERIFY_ALPHANUMERIC_00023);
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
        user.setEnable(true);
        user.setImage(false);
        userService.insertUser(user);

        // 用户信息注册时间前端显示填充
        user.setAddTimeShow(cn.hutool.core.date.DateUtil.format(user.getAddTime(), "yyyy-MM-dd HH:mm:ss"));

        // 用户详情信息
        UserInfo userInfoSave = new UserInfo();
        userInfoSave.setId(user.getId());
        userInfoSave.setEmail(email);
        userInfoService.insertUserInfo(userInfoSave);

        // 用户角色填充
        Role role = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, RoleConstant.USER));
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleService.save(userRole);

        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 60);

        this.temporaryRemember(username, response);
    }

    @Override
    public void loginEnter(String username, String password, String verifyCodeText,
                           HttpSession session, String remember, HttpServletResponse response) {
        // 对用户登录信息进行非空校验
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(verifyCodeText)){
            throw new BusinessException(UserReturnCode.USER_LOGIN_PARAM_NULL_00015);
        }

        // 判断用户是否处于锁定状态
        String userLockFlag = redisTemplate.opsForValue().get("user:lock:" + username);
        if("lock".equals(userLockFlag)){
            throw new BusinessException(UserReturnCode.USER_LOGIN_LOCK_STATUS_00021);
        }

        // 构建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, systemConfig.getSecret().getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex(password);
        User user = userService.getUserByUsernameAndPassword(username, encryptHex);
        if(ObjectUtil.isNull(user)){
            String lockCount = redisTemplate.opsForValue().get("user:lock:count:" + username);
            if(StrUtil.isNotBlank(lockCount)){
                if(Integer.valueOf(lockCount) >= UserConstant.USER_LOGIN_ERROR_COUNT - 1){
                    // 存储到redis当中
                    redisTemplate.opsForValue().set("user:lock:" + username,
                            "lock", 5, TimeUnit.MINUTES);
                }
                redisTemplate.opsForValue().increment("user:lock:count:" + username, 1);
            }else {
                // 存储到redis当中
                redisTemplate.opsForValue().set("user:lock:count:" + username,
                        "1", 5, TimeUnit.MINUTES);
            }

            throw new BusinessException(UserReturnCode.USER_LOGIN_PARAM_VERIFY_00016);
        }

        // 验证码判断
        String verifyCode = (String) session.getAttribute("verifyCode");
        if(StrUtil.isBlank(verifyCode) || !verifyCodeText.equalsIgnoreCase(verifyCode)){
            throw new BusinessException(UserReturnCode.USER_LOGIN_PARAM_VERIFY_CODE_FAIL_00020);
        }

        // 判断其他地方是否登录
        // 删除当前登录用户已绑定的HttpSession map中的remove方法返回删除value值
        HttpSession sessionMap = LonginUserSessionConfig.USER_SESSION.remove(username);

        if (sessionMap != null){
            // 删除已登录的sessionId绑定的用户
            sessionMap.removeAttribute("user");

            // 当前session销毁时删除当前session绑定的用户信息
            // 同时删除当前session绑定用户的HttpSession
            LonginUserSessionConfig.SESSION_ID_USER.remove(sessionMap.getId());
        }

        // 添加用户与HttpSession的绑定
        LonginUserSessionConfig.USER_SESSION.put(username, session);
        // 添加sessionId和用户的绑定
        LonginUserSessionConfig.SESSION_ID_USER.put(session.getId(), username);

        session.setAttribute("user", user);
        // session有效期1个小时
        session.setMaxInactiveInterval(60 * 60);

        // 记住我功能
        if("on".equals(remember)){
            this.remember(username, response);
        }else {
            this.temporaryRemember(username, response);
        }
    }

    @Override
    public void loginOut(HttpSession session, HttpServletResponse response) {
        User user = (User) session.getAttribute("user");
        if(ObjectUtil.isNull(user)){
            return;
        }

        // 覆盖掉带秘钥的cookie
        Cookie cookie = new Cookie("userLoginCipher" + user.getUsername(), "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // 删除session用户信息
        session.removeAttribute("user");
        UserContext.remove();
    }

    @Override
    public void remember(String username, HttpServletResponse response) {
        String md5 = DigestUtil.md5Hex(username + systemConfig.getSecret());
        UUID uuid = UUID.randomUUID();
        // 秘钥生成规则 MD5 + UUID
        String cipher = uuid + md5;
        Cookie cookie = new Cookie("userLoginCipher" + username, cipher);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        // 存储到 redis 当中
        redisTemplate.opsForValue().set("userLoginCipher" + username, cipher, 7 * 24 * 60, TimeUnit.MINUTES);
    }

    @Override
    public void remember(HttpServletRequest request, HttpSession session) {
        Cookie[] cookies = request.getCookies();
        // 记住我的功能 校验信息cookie和redis当中是否一致
        if(ObjectUtil.isNotNull(cookies)){
            for (Cookie cookie : cookies) {
                if(cookie.getName().length() > 15){
                    // cookie键的名字
                    String subKey = cookie.getName().substring(0, 15);
                    // 用户名
                    String subName = cookie.getName().substring(15);
                    if("userLoginCipher".equals(subKey)){
                        String redis = redisTemplate.opsForValue().get(cookie.getName());
                        if(cookie.getValue().equals(redis)){
                            User user = userService.getUserByUsername(subName);
                            if (ObjectUtil.isNull(session.getAttribute("user"))){
                                // 判断其他地方是否登录
                                // 删除当前登录用户已绑定的HttpSession map中的remove方法返回删除value值
                                HttpSession sessionMap = LonginUserSessionConfig.USER_SESSION.remove(user.getUsername());

                                if (sessionMap != null){
                                    // 删除已登录的sessionId绑定的用户
                                    sessionMap.removeAttribute("user");

                                    // 当前session销毁时删除当前session绑定的用户信息
                                    // 同时删除当前session绑定用户的HttpSession
                                    LonginUserSessionConfig.SESSION_ID_USER.remove(sessionMap.getId());
                                }

                                // 添加用户与HttpSession的绑定
                                LonginUserSessionConfig.USER_SESSION.put(user.getUsername(), session);
                                // 添加sessionId和用户的绑定
                                LonginUserSessionConfig.SESSION_ID_USER.put(session.getId(), user.getUsername());

                                session.setAttribute("user", user);
                                // session有效期1个小时
                                session.setMaxInactiveInterval(60 * 60);
                            }
                            UserContext.set(user);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public JSONObject enter(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);

        User user = (User)request.getSession().getAttribute("user");
        if(ObjectUtil.isNotNull(user)){
            UserContext.set(user);
            jsonObject.put("data", "ok");
        }else {
            this.remember(request, request.getSession());
            user = (User)request.getSession().getAttribute("user");
            if(ObjectUtil.isNotNull(user)){
                UserContext.set(user);
                jsonObject.put("data", "ok");
            }
        }

        if(ObjectUtil.isNull(user)){
            jsonObject.put("data", "fail");
        }

        return jsonObject;
    }

    @Override
    public void temporaryRemember(String username, HttpServletResponse response) {
        String md5 = DigestUtil.md5Hex(username + systemConfig.getSecret());
        UUID uuid = UUID.randomUUID();
        // 秘钥生成规则 MD5 + UUID
        String cipher = uuid + md5;
        Cookie cookie = new Cookie("userLoginCipher" + username, cipher);
        cookie.setPath("/");
        cookie.setMaxAge(2 * 60 * 60);
        response.addCookie(cookie);

        // 存储到 redis 当中
        redisTemplate.opsForValue().set("userLoginCipher" + username, cipher, 2 * 60, TimeUnit.MINUTES);
    }

    @Override
    public void verifyUserWebSocket(String userName, String userPassword) {
        // 进行用户信息参数非空校验
        if(StrUtil.isBlank(userName) || StrUtil.isBlank(userPassword)){
            throw new BusinessException(NettyCode.USER_INFO_VERIFY_PARAM_NULL_FAIL_00000);
        }
        // 开始校验用户信息
        String redis = redisTemplate.opsForValue().get("userLoginCipher" + userName);
        if(!userPassword.equals(redis)){
            throw new BusinessException(NettyCode.USER_INFO_VERIFY_PARAM_FAIL_00000);
        }
    }
}
