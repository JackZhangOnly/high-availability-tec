package com.jackzhang.rabbitmq.multiconsumber;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者2
 * Created by Jack on 2018/3/25.
 */
public class Worker2 {

    private final static String MQUEUE_NAME = "message_task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");
        Connection connection = connectionFactory.newConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare(MQUEUE_NAME, true, false, false, null);

        System.out.println("Worker2 [*] Waiting for messages. To exit press CTRL + C");

        // 每次从队列中获取数量 (accept only one unack-ed message at a time)
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Worker2 [x] Received '" + message + "'");

                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker2 [x] Done");
                    // 消息处理完成确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        channel.basicConsume(MQUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}