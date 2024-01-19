package cc.langhai.service.api.impl;

import cc.langhai.service.api.WebSiteApiService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 网站 api相关service实现类
 *
 * @author langhai
 * @date 2024-01-19 13:35
 */
@Service
public class WebSiteApiServiceImpl implements WebSiteApiService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String pageView(String websiteName, String serverName) {
        String pageView = redisTemplate.opsForValue().get("pageViewApi:" + serverName + ":" + websiteName);
        if (StrUtil.isBlank(pageView)) {
            // 存储到redis当中
            redisTemplate.opsForValue().set("pageViewApi:" + serverName + ":" + websiteName,
                    "1", 7 * 24 * 60, TimeUnit.MINUTES);
            return "1";
        } else {
            pageView = String.valueOf(Long.valueOf(pageView) + 1);
            // 存储到redis当中
            redisTemplate.opsForValue().set("pageViewApi:" + serverName + ":" + websiteName,
                    pageView, 7 * 24 * 60, TimeUnit.MINUTES);
            return pageView;
        }
    }

    @Override
    public String score(String websiteUrl, String whois) {
        Integer score = 100;
        // 访问速度测试
        long startTime = System.currentTimeMillis();
        String websiteHtml = HttpUtil.get(websiteUrl);
        long endTime = System.currentTimeMillis();
        long visitTime = endTime - startTime;
        if (visitTime >= 300 && visitTime <= 500) {
            score = score - 5;
        } else if (visitTime > 500) {
            score = score - 10;
        }
        // 百度网站流量评估
        String urlWithoutProtocol = websiteUrl.replaceFirst("^(http://|https://)", "");
        String ipCount = HttpUtil.get("https://apistore.aizhan.com/baidurank/siteinfos/17bb2000a3455e50e12ff399adb24ad0?domains=" + urlWithoutProtocol);
        JSONObject ipBean = JSONUtil.toBean(ipCount, JSONObject.class);
        if (Integer.valueOf(200000).equals(ipBean.get("code"))) {
            JSONObject data = (JSONObject) ipBean.get("data");
            JSONArray jsonArray = (JSONArray) data.get("success");
            JSONObject ipData = (JSONObject) jsonArray.get(0);
            String ipDataStr = (String) ipData.get("ip");
            Long ipDataLong = Long.valueOf(ipDataStr.split(" ~ ")[0]);
            if (ipDataLong >= 0L && ipDataLong <= 100L) {
                score = score - 15;
            } else if (ipDataLong >= 101L && ipDataLong <= 1000L) {
                score = score - 10;
            } else if (ipDataLong >= 1001L && ipDataLong <= 2000L) {
                score = score - 5;
            }
        } else {
            score = score - 20;
        }
        // 域名注册时间
        String whoisJson = HttpUtil.get("https://www.yuanxiapi.cn/api/domain_whois/?domain=" + whois);
        JSONObject whoisBean = JSONUtil.toBean(whoisJson, JSONObject.class);
        String registrationTime = (String) whoisBean.get("Registration_Time");
        long betweenDay = DateUtil.between(DateUtil.parse(registrationTime), new Date(), DateUnit.DAY);
        if (betweenDay >= 0L && betweenDay <= 365L) {
            score = score - 5;
        } else if (betweenDay >= 366L && betweenDay <= 500L) {
            score = score - 3;
        } else if (betweenDay >= 501L && betweenDay <= 700L) {
            score = score - 1;
        }
        return String.valueOf(score);
    }

}
