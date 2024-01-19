package cc.langhai.service.api;

import javax.servlet.http.HttpServletRequest;

/**
 * 网站 api相关service
 *
 * @author langhai
 * @date 2024-01-19 13:34
 */
public interface WebSiteApiService {

    /**
     * 网站访问量增加1个，七天没有新增访问量，自动清零。
     *
     * @param websiteName 网站名字
     * @param serverName 服务站点
     * @return 网站访问量结果
     */
    String pageView(String websiteName, String serverName);

    /**
     * 网站评分
     *
     * @param websiteUrl 网站url
     * @param whois 域名主体
     * @return 网站评分结果
     */
    String score(String websiteUrl, String whois);
}
