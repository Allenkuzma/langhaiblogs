package cc.langhai.service.api;


import java.util.HashMap;

/**
 * token api相关service
 *
 * @author langhai
 * @date 2024-01-05 09:39
 */
public interface TokenApiService {


    /**
     * 生成token
     *
     * @param consumer 消费者
     * @param userId 用户id
     * @param expTime 过期时间 单位：秒
     * @return token
     */
    String generateToken(String consumer, String userId, Integer expTime);

    /**
     * 校验token
     *
     * @param tokenMap token参数
     * @return token校验结果
     */
    HashMap<String, Object> checkToken(HashMap<String, String> tokenMap);
}
