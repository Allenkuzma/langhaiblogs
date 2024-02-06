package cc.langhai.service.api.impl;

import cc.langhai.exception.BusinessException;
import cc.langhai.service.api.TokenApiService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * token api相关service实现类
 *
 * @author langhai
 * @date 2024-01-05 09:41
 */
@Service
public class TokenApiServiceImpl implements TokenApiService {

    @Override
    public String generateToken(String consumer, String userId, Integer expTime) {
        // TODO consumer 校验
        List<String> consumerList = Arrays.asList("100", "200", "300", "400", "500");
        if (!consumerList.contains(consumer)) {
            throw new BusinessException(500, "token消费者校验失败。");
        }
        // 生成token
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, expTime);
        String token = JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256("lang-hai"));
        return token;
    }

    @Override
    public HashMap<String, Object> checkToken(HashMap<String, String> tokenMap) {
        String token = tokenMap.get("token");
        if (StrUtil.isBlank(token)) {
            throw new BusinessException(500, "token校验失败。");
        }
        // 校验token
        DecodedJWT decodedJWT = null;
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("lang-hai")).build();
            decodedJWT = jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new BusinessException(500, "token校验失败。");
        }
        String userId = decodedJWT.getClaim("userId").asString();
        Date expiresAt = decodedJWT.getExpiresAt();
        HashMap<String, Object> result = new HashMap<>(1);
        result.put("userId", userId);
        result.put("expTime", DateUtil.format(expiresAt, "yyyy-MM-dd HH:mm:ss"));
        return result;
    }
}
