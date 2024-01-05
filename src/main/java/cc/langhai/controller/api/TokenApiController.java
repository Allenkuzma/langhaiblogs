package cc.langhai.controller.api;

import cc.langhai.response.ResultResponse;
import cc.langhai.service.api.TokenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * api token控制器
 *
 * @author langhai
 * @date 2024-01-05 09:31
 */
@Controller
@RequestMapping("/api/token")
public class TokenApiController {

    @Autowired
    private TokenApiService tokenApiService;

    /**
     * 生成token
     *
     * @param consumer 消费者
     * @param userId 用户id
     * @param expTime 过期时间 单位：秒
     * @return token
     */
    @ResponseBody
    @GetMapping("/generateToken")
    public ResultResponse<String> generateToken(@RequestParam(value = "consumer") String consumer,
                                                @RequestParam(value = "userId") String userId,
                                                @RequestParam(value = "expTime") Integer expTime) {
        String token = tokenApiService.generateToken(consumer, userId, expTime);
        return new ResultResponse<String>(200, "token生成成功。", token);
    }

    /**
     * 校验token
     *
     * @param tokenMap token参数
     * @return token校验结果
     */
    @ResponseBody
    @PostMapping("/checkToken")
    public ResultResponse<HashMap<String, Object>> checkToken(@RequestBody HashMap<String, String> tokenMap) {
        HashMap<String, Object> result = tokenApiService.checkToken(tokenMap);
        return new ResultResponse<HashMap<String, Object>>(200, "token校验成功。", result);
    }
}
