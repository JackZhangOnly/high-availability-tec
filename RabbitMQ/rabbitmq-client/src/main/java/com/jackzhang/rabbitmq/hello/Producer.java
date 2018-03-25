package com.jackzhang.rabbitmq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 * Created by Jack on 2018/3/25.
 */
public class Producer {
    private final static String MQUEUE_NAME="Hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory=new ConnectionFactory();

        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");
        //创建一个新的连接
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();

        // 声明一个队列 -- 在 RabbitMQ 中, 队列的声明是幂等性的
        // 一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同
        // 也就是说, 如果不存在, 就创建, 如果存在, 不会对已经存在的队列产生任何影响
        // 如果并不知道是生产者还是消费者程序中的哪个先运行，在程序中重复将队列重复声明一下是种值得推荐的做法
        channel.queueDeclare(MQUEUE_NAME, false, false, false, null);
        String message = "hello world";
        // 发送到消息队列中
        channel.basicPublish("", MQUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("P [x] Sent '" + message + "'");
        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}
