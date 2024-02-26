package cc.langhai.service.system.impl;

import cc.langhai.exception.BusinessException;
import cc.langhai.service.system.NotificationService;
import cc.langhai.utils.DateUtil;
import cc.langhai.utils.UserContext;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 后台通知 service接口 实现类
 *
 * @author langhai
 * @date 2024-02-21 15:54
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addNotification(String notification, String seconds, String href) {
        if (StrUtil.isBlank(notification) || StrUtil.isBlank(seconds) || StrUtil.isBlank(href)) {
            throw new BusinessException(500, "必填项为空！");
        }
        Long userId = UserContext.getUserId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("href", href);
        jsonObject.put("notification", notification);
        redisTemplate.opsForValue().set("notification:" + userId + ":" + DateUtil.getNowDayDetail(), jsonObject.toJSONString(), Long.parseLong(seconds), TimeUnit.SECONDS);
    }

    @Override
    public JSONObject getAllNotification() {
        ArrayList<JSONObject> result = getJsonObjects();
        JSONObject jsonObject = new JSONObject();
        if (!result.isEmpty()) {
            jsonObject.put("id", 1);
            jsonObject.put("title", "通知");
            jsonObject.put("children", result);
        }
        return jsonObject;
    }

    private ArrayList<JSONObject> getJsonObjects() {
        ArrayList<JSONObject> result = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("notification:*");
        int i = 10;
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            JSONObject notification = (JSONObject) JSONObject.parse(value);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i++);
            jsonObject.put("avatar", "https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png");
            jsonObject.put("title", notification.get("notification"));
            jsonObject.put("context", notification.get("notification"));
            jsonObject.put("href", notification.get("href"));
            jsonObject.put("key", key);
            jsonObject.put("expire", redisTemplate.getExpire(key));
            // 获取第一个:索引
            int index = key.indexOf(":");
            // 获取第二个:索引
            index = key.indexOf(":", index + 1);
            // 截取第二个:之后字符串
            jsonObject.put("time", key.substring(index + 1));
            result.add(jsonObject);
        }
        return result;
    }

    @Override
    public ArrayList<JSONObject> list() {
        return getJsonObjects();
    }

    @Override
    public void deleteNotification(String key) {
        redisTemplate.delete(key);
    }
}
