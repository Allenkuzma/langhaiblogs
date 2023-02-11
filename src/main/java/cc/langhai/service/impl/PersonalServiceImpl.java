package cc.langhai.service.impl;

import cc.langhai.domain.User;
import cc.langhai.domain.UserInfo;
import cc.langhai.exception.BusinessException;
import cc.langhai.response.ResultResponse;
import cc.langhai.response.UserReturnCode;
import cc.langhai.service.PersonalService;
import cc.langhai.service.UserInfoService;
import cc.langhai.service.UserService;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户个人空间 service接口 实现类
 *
 * @author langhai
 * @date 2022-12-21 22:03
 */
@Service
public class PersonalServiceImpl implements PersonalService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(String nickname, String motto, HttpSession session) {
        if(StrUtil.isBlank(nickname) || StrUtil.isBlank(motto)){
            throw new BusinessException(UserReturnCode.USER_PERSONAL_UPDATE_USER_INFO_PARAM_FAIL_00019);
        }

        // 对昵称进行合法校验 昵称（1到12位的字符组合）
        if(nickname.length() < 1 || nickname.length() > 12){
            throw new BusinessException(UserReturnCode.USER_PERSONAL_UPDATE_USER_INFO_PARAM_FAIL_00019);
        }

        // 对座右铭进行合法校验 座右铭（1到128位的字符组合）
        if(motto.length() < 1 || motto.length() > 128){
            throw new BusinessException(UserReturnCode.USER_PERSONAL_UPDATE_USER_INFO_PARAM_FAIL_00019);
        }

        Long userId = UserContext.getUserId();
        // 更改用户信息表
        User user = userService.getUserById(userId);
        if(!nickname.equals(user.getNickname())){
            user.setNickname(nickname);
            user.setUpdateTime(new Date());
            userService.updateUser(user);

            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60 * 60);
        }
        // 更改用户详情表
        UserInfo userInfo = userInfoService.getUserInfoById(userId);
        if(!motto.equals(userInfo.getMotto())){
            userInfo.setMotto(motto);
            userInfo.setUpdateTime(new Date());
            userInfoService.updateUserInfo(userInfo);
        }
    }

}
