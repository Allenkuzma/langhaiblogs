package cc.langhai.mq.config;

/**
 * rabbitMQ交换机和队列声明
 *
 * @author langhai
 * @date 2023-01-09 10:26
 */
public class MqConstants {

    /**
     * 交换机
     */
    public final static String BLOGS_EXCHANGE = "blogs.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String BLOGS_INSERT_QUEUE = "blogs.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String BLOGS_DELETE_QUEUE = "blogs.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String BLOGS_INSERT_KEY = "blogs.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String BLOGS_DELETE_KEY = "blogs.delete";
}