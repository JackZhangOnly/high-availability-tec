package com.jackzhang.rabbitmq.publishsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 * 1） RabbitMQ消息模型的核心理念是：发布者（producer）不会直接发送任何消息给队列。事实上
 * 发布者（producer）甚至不知道消息是否已经被投递到队列。
 * 2）前面的教程中我们对交换机一无所知，但仍然能够发送消息到队列中。因为我们使用了命名为空字符串("")默认的交换机。
 * 回想我们之前是如何发布一则消息：
 * channel.basic_publish(exchange='',
 * routing_key='hello',
 * body=message)
 * exchange参数就是交换机的名称。空字符串代表默认或者匿名交换机：消息将会根据指定的routing_key分发到指定的队列。
 * 3）当声明交换器的类型的fanout时，routingkey不起作用。
 *
 * Created by Jack on 2018/3/25.
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "log";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        

        /**
         * 声明交换器为fanout类型，连接在交换器上的队列均能收到生产者的消息（相当于广播）
         * Direct exchange：直连交换机，转发消息到routigKey指定的队列，如果消息的routigKey和binding的routigKey直接匹配的话，消息将会路由到该队列
         * Fanout exchange：扇形交换机，转发消息到所有绑定队列（速度最快），不管消息的routigKey息和binding的参数表头部信息和值是什么
         * 消息将会路由到所有的队列
         * Topic exchange：主题交换机，按规则转发消息（最灵活），如果消息的routigKey和binding的routigKey符合通配符匹配的话，消息将会路由到该队列
         * Headers exchange：首部交换机 ，如果消息的头部信息和binding的参数表中匹配的话，消息将会路由到该队列。
         */
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i= 0; i < 5; i++) {
            String message = "Hello World! " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }
}