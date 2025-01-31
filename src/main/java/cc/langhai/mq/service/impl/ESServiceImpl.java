package cc.langhai.mq.service.impl;

import cc.langhai.domain.Article;
import cc.langhai.exception.BusinessException;
import cc.langhai.mq.service.ESService;
import cc.langhai.response.ESReturnCode;
import cc.langhai.service.ArticleService;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * es搜索引擎 service接口 实现类
 *
 * @author langhai
 * @date 2023-01-09 10:39
 */
@Service
public class ESServiceImpl implements ESService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ArticleService articleService;

    @Override
    public void deleteById(Long id) {
        try {
            // 1.准备Request
            DeleteRequest request = new DeleteRequest("langhaiblogs", id.toString());
            // 2.发送请求
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new BusinessException(ESReturnCode.ES_DOC_DELETE_FAIL_00000);
        }
    }

    @Override
    public void insertById(Long id) {
        try {
            // 0.根据id查询文章数据
            Article article = articleService.getById(id);
            // 1.准备Request对象
            IndexRequest request = new IndexRequest("langhaiblogs").id(article.getId().toString());
            // 2.准备Json文档
            request.source(JSON.toJSONString(article), XContentType.JSON);
            // 3.发送请求
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new BusinessException(ESReturnCode.ES_DOC_UPDATE_FAIL_00001);
        }
    }

    @Override
    public Article getById(Long id) throws IOException {
        // 1.准备Request
        GetRequest request = new GetRequest("langhaiblogs", id.toString());
        // 2.发送请求，得到响应
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        // 3.解析响应结果
        String json = response.getSourceAsString();
        Article article = JSON.parseObject(json, Article.class);
        return article;
    }
}
