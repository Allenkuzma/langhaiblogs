package cc.langhai.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ 配置类 绑定关系
 *
 * @author langhai
 * @date 2023-01-09 10:29
 */
@Configuration
public class MqConfig {

    /**
     * 文章交换机
     *
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MqConstants.BLOGS_EXCHANGE, true, false);
    }

    /**
     * 更新文章队列
     *
     * @return Queue
     */
    @Bean
    public Queue insertQueue() {
        return new Queue(MqConstants.BLOGS_INSERT_QUEUE, true);
    }

    /**
     * 删除文章队列
     *
     * @return Queue
     */
    @Bean
    public Queue deleteQueue() {
        return new Queue(MqConstants.BLOGS_DELETE_QUEUE, true);
    }

    /**
     * 绑定关系
     *
     * @return Binding
     */
    @Bean
    public Binding insertQueueBinding() {
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(MqConstants.BLOGS_INSERT_KEY);
    }

    /**
     * 绑定关系
     *
     * @return Binding
     */
    @Bean
    public Binding deleteQueueBinding() {
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(MqConstants.BLOGS_DELETE_KEY);
    }
}