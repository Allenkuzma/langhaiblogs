package cc.langhai.mq.config;

import cc.langhai.domain.Article;
import cc.langhai.mq.service.ESService;
import cc.langhai.service.ArticleService;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * mq文章模块Listener
 *
 * @author langhai
 * @date 2023-01-09 10:47
 */
@Component
public class ArticleListener {

    @Autowired
    private ESService esService;

    @Autowired
    private ArticleService articleService;

    /**
     * 监听文章新增或修改的业务
     *
     * @param id 文章id
     */
    @RabbitListener(queues = MqConstants.BLOGS_INSERT_QUEUE)
    public void listenHotelInsertOrUpdate(Long id) throws IOException {
        Article article = articleService.getById(id);
        // 文章更新 公开 ==>> 不公开
        Article esServiceById = esService.getById(id);
        if (ObjectUtil.isNotNull(esServiceById)) {
            esService.deleteById(id);
        }

        if (article.getPublicShow().equals(1) && article.getCheckFlag().equals(1)) {
            esService.insertById(id);
        }
    }

    /**
     * 监听文章删除的业务
     * @param id 文章id
     */
    @RabbitListener(queues = MqConstants.BLOGS_DELETE_QUEUE)
    public void listenHotelDelete(Long id){
        esService.deleteById(id);
    }
}