package cc.langhai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 博客程序启动器
 *
 * @author langhai
 * @date 2022-11-19 16:01
 */
@EnableAsync
@ServletComponentScan
@SpringBootApplication
public class BlogsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogsApplication.class, args);
    }
}
