package cc.langhai.utils;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户ip地址工具
 *
 * @author langhai
 * @date 2022-11-22
 */
public class IPUtil {

    /**
     * 获取用户真实ip地址
     *
     * @return
     */
    public static String getIP(HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        String header = request.getHeader("x-forwarded-for");
        String ip = "";
        if(StrUtil.isBlank(header)){
            ip = remoteAddr;
        }else {
            ip = header;
        }

        return ip;
    }
}
