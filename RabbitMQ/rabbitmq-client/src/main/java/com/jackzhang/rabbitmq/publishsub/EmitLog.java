package com.jackzhang.rabbitmq.publishsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
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