package cc.langhai.es;

import cc.langhai.domain.Article;
import cc.langhai.es.config.ESConstants;
import cc.langhai.es.config.ESProp;
import cc.langhai.mapper.ArticleMapper;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * 此测试用例 包含ES基本操作
 *
 * @author langhai
 * @date 2023-01-08 16:21
 */
@SpringBootTest
public class ESTest {

    private RestHighLevelClient client;

    @Autowired
    private ESProp esProp;

    @Autowired
    private ArticleMapper articleMapper;

    @BeforeEach
    void setUp() {
        // 使用账户密码
        HttpHost host = new HttpHost(esProp.getIp(), Integer.valueOf(esProp.getPort()), HttpHost.DEFAULT_SCHEME_NAME);

        RestClientBuilder builder = RestClient.builder(host);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esProp.getUserName(), esProp.getPassword()));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));

        this.client = new RestHighLevelClient(builder);
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    /**
     * 创建浪海博客系统的文章索引
     *
     * @throws IOException
     */
    @Test
    void createHotelIndex() throws IOException {
        // 1.创建Request对象
        CreateIndexRequest request = new CreateIndexRequest("langhaiblogs");
        // 2.准备请求的参数：DSL语句
        request.source(ESConstants.MAPPING_TEMPLATE, XContentType.JSON);
        // 3.发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除浪海博客系统的文章索引
     *
     * @throws IOException
     */
    @Test
    void testDeleteHotelIndex() throws IOException {
        // 1.创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("langhaiblogs");
        // 2.发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    /**
     * 判断海博客系统的文章索引是否存在
     *
     * @throws IOException
     */
    @Test
    void testExistsHotelIndex() throws IOException {
        // 1.创建Request对象
        GetIndexRequest request = new GetIndexRequest("langhaiblogs");
        // 2.发送请求
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        // 3.输出
        System.err.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }

    /**
     * 批量增加文档内容
     *
     * @throws IOException
     */
    @Test
    void testBulkRequest() throws IOException {
        // 批量查询酒店数据
        List<Article> allArticlePublicShow = articleMapper.getAllArticlePublicShow("");

        // 1.创建Request
        BulkRequest request = new BulkRequest();
        // 2.准备参数，添加多个新增的Request
        for (Article article : allArticlePublicShow) {
            // 2.1.创建新增文档的Request对象
            request.add(new IndexRequest("langhaiblogs")
                    .id(article.getId().toString())
                    .source(JSON.toJSONString(article), XContentType.JSON));
        }
        // 3.发送请求
        client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * 按条件查询
     *
     * @throws IOException
     */
    @Test
    void testMatch() throws IOException {
        // 1.准备Request
        SearchRequest request = new SearchRequest("langhaiblogs");
        // 2.准备DSL
        request.source()
                .query(QueryBuilders.multiMatchQuery("浪海", "title", "author", "labelContent"));
        // 3.发送请求
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.解析响应
        handleResponse(response);

    }

    /**
     * 处理搜索结果
     *
     * @param response
     */
    private void handleResponse(SearchResponse response) {
        // 4.解析响应
        SearchHits searchHits = response.getHits();
        // 4.1.获取总条数
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到" + total + "条数据");
        // 4.2.文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        for (SearchHit hit : hits) {
            // 获取文档source
            String json = hit.getSourceAsString();
            // 反序列化
            Article article = JSON.parseObject(json, Article.class);
            System.out.println("article = " + article);
        }
    }

    /**
     * id 查询文档
     * @throws IOException
     */
    @Test
    void testGetDocumentById() throws IOException {
        // 1.准备Request
        GetRequest request = new GetRequest("langhaiblogs", "30");
        // 2.发送请求，得到响应
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();

        Article article = JSON.parseObject(json, Article.class);
        System.out.println(article);
    }

    /**
     * 删除文档 id
     *
     * @throws IOException
     */
    @Test
    void testDeleteDocument() throws IOException {
        // 1.准备Request
        DeleteRequest request = new DeleteRequest("langhaiblogs", "30");
        // 2.发送请求
        client.delete(request, RequestOptions.DEFAULT);
    }
}
