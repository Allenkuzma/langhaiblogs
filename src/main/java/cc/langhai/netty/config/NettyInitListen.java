package cc.langhai.netty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * netty初始化
 *
 * @author langhai
 * @date 2023-03-23 19:20
 */
@Component
public class NettyInitListen implements CommandLineRunner {

    @Value("${netty.port}")
    Integer nettyPort;
    @Value("${server.port}")
    Integer serverPort;

    @Async
    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("浪海博客 ===>>> nettyServer starting ...");
            // System.out.println("浪海博客 Netty ===>>> http://127.0.0.1:" + serverPort + "/loginNetty");
            new NettyServer(nettyPort).start();
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }
}