package com.jackzhang.rabbitmq.multiconsumber;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
/**
 * 消息推送任务
 * 默认来说，RabbitMQ会按顺序得把消息发送给每个消费者（consumer）。
 * 平均每个消费者都会收到同等数量得消息。这种发送消息得方式叫做——轮询（round-robin）。
 * Created by Jack on 2018/3/25.
 */
public class PushTask {

    private final static String MQUEUE_NAME = "message_task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(MQUEUE_NAME, true, false, false, null);

        for(int i = 0; i < 5; i++) {
            String message = "new message " + i;
            channel.basicPublish("", MQUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] sent ' " + message + " '");
        }

        channel.close();
        connection.close();
    }
}