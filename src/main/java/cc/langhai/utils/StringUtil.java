package cc.langhai.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author langhai
 * @date 2022-12-08 21:45
 */
public class StringUtil {

    /**
     * 判断字符串是否由数字和字母组成
     *
     * @param s 需要判断的字符串
     * @return 结果
     */
    public static boolean isAlphaNumeric(String s) {
        Matcher m = Pattern.compile("[0-9a-zA-Z]{1,}").matcher(s);
        return m.matches();
    }
}
