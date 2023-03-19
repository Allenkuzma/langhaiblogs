package cc.langhai.utils;


import cc.langhai.exception.BusinessException;
import cc.langhai.response.UserReturnCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮箱工具类
 *
 * @author langhai
 * @date 2022-11-21 22:09
 */
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送者邮箱号码
     */
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件验证码
     *
     * @param email
     * @return
     */
    public String send(String email){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String random = RandomStringUtils.randomNumeric(6);
        simpleMailMessage.setText("注册验证码：" + random + "~~ 网址www.langhai.cc ~~" + "有效期5分钟");
        simpleMailMessage.setSubject("浪海博客注册验证码");
        simpleMailMessage.setFrom(from);
        // 收件人的邮箱地址
        simpleMailMessage.setTo(email);

        try {
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new BusinessException(UserReturnCode.EMAIL_CODE_00001);
        }

        return random;
    }
}
