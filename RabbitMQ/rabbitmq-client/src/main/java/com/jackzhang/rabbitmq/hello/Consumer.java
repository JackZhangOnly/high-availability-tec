package com.jackzhang.rabbitmq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 * Created by Jack on 2018/3/25.
 */
public class Consumer {

    private static final String MQUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置 RabbitMQ 地址
        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");
        // 创建一个新的连接
        Connection connection = connectionFactory.newConnection();
        // 创建一个频道
        Channel channel = connection.createChannel();
        // 声明要关注的队列
        channel.queueDeclare(MQUEUE_NAME, false, false, false, null);

        // DefaultConsumer类 实现了 Consumer 接口, 通过传入一个频道, 告诉服务器我们需要哪个频道的消息
        // 如果频道中有消息, 就会执行回调函数 handleDelivery
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("C [x] Received '" + message + "'");
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制
        channel.basicConsume(MQUEUE_NAME, true, consumer);
    }
}