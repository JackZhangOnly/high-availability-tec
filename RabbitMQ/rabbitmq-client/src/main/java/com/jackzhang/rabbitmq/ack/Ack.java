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
 * 若一直不执行basicAck，则状态就一直保持不变
 * 再如果将consumer关掉，则unacked的msg又回到了ready。
 * 这就是consumer的确认机制，来保证把消息真正的处理完后，告诉MQ，让其删除。
 * 若consumer没有ack，则MQ就不会删除，即使consumer死掉，MQ又会把消息发给其他consumer（如果有其他consumer的话）
 *
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
                                //消息处理
                                System.out.println("got msg : " + new String(body)+"====tag"+deliveryTag);
                                //basicAck中的第二个参数若是true，则表示小于等于这个tag的全部发ack，类似于批量ack
                                ch.basicAck(deliveryTag, false);
                            }
                });

                /**
                 * QueueingConsumer内部其实是一个LinkBlockingQueue，它将从broker端接受到的信息先暂存到这个LinkBlockingQueue中
                 * 然后消费端程序在从这个LinkBlockingQueue中take出消息。试下一下，如果我们不take消息或者说take的非常慢
                 * 那么LinkBlockingQueue中的消息就会越来越多，最终造成内存溢出。
                 */
                /*
                过期用法
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
