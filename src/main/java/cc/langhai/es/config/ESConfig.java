package cc.langhai.es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * es搜索引擎配置类
 *
 * @author langhai
 * @date 2023-01-08 16:58
 */
@Configuration
public class ESConfig {

    @Autowired
    private ESProp esProp;

    @Bean
    public RestHighLevelClient client(){
        // 使用账户密码
        HttpHost host = new HttpHost(esProp.getIp(), Integer.valueOf(esProp.getPort()), HttpHost.DEFAULT_SCHEME_NAME);

        RestClientBuilder builder = RestClient.builder(host);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esProp.getUserName(), esProp.getPassword()));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }
}
