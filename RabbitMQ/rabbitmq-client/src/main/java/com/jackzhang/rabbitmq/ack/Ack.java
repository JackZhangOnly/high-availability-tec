package com.jackzhang.rabbitmq.ack;

import com.jackzhang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *  rabbitmq ack
 * Created by Jack on 2018/3/27.
 */
public class Ack {

    private static final String QUEUE_NAME = "publisher-confirms";

    public static void main(String[] args) throws Exception {
        // Consume MSG_COUNT messages.
        (new Thread(new Consumer())).start();
    }
    static class Consumer implements Runnable {

        public void run() {

            try {
                Connection conn = ConnectionUtil.getConnectionMq();
                // Setup
                final Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume
                boolean autoAck = false;
                ch.basicConsume(QUEUE_NAME, autoAck,
                        new DefaultConsumer(ch) {
                         @Override
                           public void handleDelivery(String consumerTag,
                            Envelope envelope,
                            AMQP.BasicProperties properties,
                            byte[] body)
                            throws IOException
                            {
                                String routingKey = envelope.getRoutingKey();
                                String contentType = properties.getContentType();
                                long deliveryTag = envelope.getDeliveryTag();
                                //(process the message components here ...);
                                System.out.println("got msg : " + new String(body)+"====tag"+deliveryTag);

                                ch.basicAck(deliveryTag, false);
                            }
                });
                /*
                QueueingConsumer qc = new QueueingConsumer(ch);
                ch.basicConsume(QUEUE_NAME, true, qc);
                for (int i = 0; i < MSG_COUNT; ++i) {
                    QueueingConsumer.Delivery delivery = qc.nextDelivery();
                    System.out.println("got msg : " + new String(delivery.getBody()));
                }*/


            } catch (Throwable e) {
                System.out.println("whoosh !");
                e.printStackTrace();
            }finally {
                System.out.println("finally !");

            }
        }
    }
}
