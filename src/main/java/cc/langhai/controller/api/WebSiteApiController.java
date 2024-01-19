package cc.langhai.controller.api;

import cc.langhai.response.ResultResponse;
import cc.langhai.service.api.WebSiteApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站 api控制器
 *
 * @author langhai
 * @date 2024-01-19 11:57
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api/website")
public class WebSiteApiController {

    @Autowired
    private WebSiteApiService webSiteApiService;


    /**
     * 网站访问量增加1个，七天没有新增访问量，自动清零。
     *
     * @param websiteName 网站名字
     * @param serverName 服务站点
     * @return 网站访问量结果
     */
    @ResponseBody
    @GetMapping("/pageView")
    public ResultResponse<String> pageView(@RequestParam(value = "websiteName") String websiteName,
                                           @RequestParam(value = "serverName") String serverName) {
        String pageView = webSiteApiService.pageView(websiteName, serverName);
        return new ResultResponse<>(200, "网站访问量获取成功。", pageView);
    }

    /**
     * 网站评分
     * 规则：访问速度测试 百度网站流量评估 域名注册时间
     *
     * @param websiteUrl 网站url
     * @param whois 域名主体
     * @return 网站评分结果
     */
    @ResponseBody
    @GetMapping("/score")
    public ResultResponse<String> score(@RequestParam(value = "websiteUrl") String websiteUrl,
                                        @RequestParam(value = "whois") String whois) {
        String score = webSiteApiService.score(websiteUrl, whois);
        return new ResultResponse<>(200, "网站评分获取成功。", score);
    }
}
