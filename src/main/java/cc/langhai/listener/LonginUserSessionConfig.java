package cc.langhai.listener;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * session和用户绑定配置类
 *
 * @author langhai
 * @date 2023-01-12 14:57
 */
public class LonginUserSessionConfig {

    /**
     * sessionId和用户的绑定关系
     */
    public static final Map<String, String> SESSION_ID_USER = new HashMap<String, String>();

    /**
     * 用户和Session绑定关系
     */
    public static final Map<String, HttpSession> USER_SESSION = new HashMap<String, HttpSession>();
}
