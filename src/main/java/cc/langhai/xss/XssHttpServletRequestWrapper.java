package cc.langhai.xss;

import cn.hutool.http.HtmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * xss requestWrapper
 *
 * @author langhai
 * @date 2024-01-22 15:00
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        // 过滤html标记
        return HtmlUtil.filter(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        // 过滤html标记
        return  HtmlUtil.filter(value);
    }
	
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values == null) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            if (StringUtils.isEmpty(values[i])) {
                values[i] = values[i];
            } else {
            	// 过滤html标记
                values[i] = HtmlUtil.filter(values[i]);
            }
        }
        return values;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
    	// 请求方法为POST时会触发这个方法
        return getInputStreamWithXSSFilter();
    }

    public ServletInputStream getInputStreamWithXSSFilter() throws IOException {
    	// 从inputStream读取字符串
        InputStream in = super.getInputStream();
        StringBuffer body = new StringBuffer();
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        while (line != null) {
            body.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        reader.close();
        in.close();

		// 使用hutool的Html工具类过滤
        String str = HtmlUtil.filter(body.toString());
        // 双引号不需要过滤因此替换回原来的符号
        str = str.replace("&quot;","\"");
        // 因为request里面的inputStream已经读取过,指针指向结尾重新读取会发生异常, 
        // 而且过滤后的字符串可能和之前的字符串不同,因此将过滤后的字符串重新转成inputStream返回
        final ByteArrayInputStream bain = new ByteArrayInputStream(str.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bain.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }
}
