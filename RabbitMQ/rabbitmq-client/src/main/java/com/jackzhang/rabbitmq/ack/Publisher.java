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
public class Publisher {

    private static final String QUEUE_NAME = "publisher-confirms";
    private static final int MSG_COUNT = 10;

    public static void main(String[] args) throws Exception {
        // Publish MSG_COUNT messages and wait for confirms.
        (new Thread(new Producer())).start();
    }

    static class Producer implements Runnable {

        volatile SortedSet<Long> ackSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

         public void run() {
             try {
                 long startTime = System.currentTimeMillis();

                 // 创建一个新的连接
                 Connection connection = ConnectionUtil.getConnectionMq();
                 // 创建一个频道
                 Channel channel = connection.createChannel();

                 channel.queueDeclare(QUEUE_NAME, true, false, false, null);

                 channel.confirmSelect();

                 channel.addConfirmListener(new ConfirmListener() {
                     public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                         if (multiple) {
                             for (long i = ackSet.first(); i <= deliveryTag; ++i) {
                                 System.out.println("handle ack multiple, tag : " + deliveryTag);
                                 ackSet.remove(i);
                             }
                         } else {
                             System.out.println("handle ack, tag : " + deliveryTag);
                             ackSet.remove(deliveryTag);
                         }
                     }

                     public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                         System.out.println("handle nack, tag : " + deliveryTag);
                     }
                 });

                 // Publish
                 for (long i = 0; i < MSG_COUNT; ++i) {
                     ackSet.add(i);
                     channel.basicPublish("", QUEUE_NAME,
                             MessageProperties.PERSISTENT_TEXT_PLAIN,
                             "push message".getBytes());
                     System.out.println("send msg : " + "push message");
                 }

                 // Wait
                 Thread.currentThread().sleep(1000*30);

                 // Cleanup
                 channel.close();
                 connection.close();

                 long endTime = System.currentTimeMillis();
                 System.out.printf("Test took %.3fs\n", (float) (endTime - startTime) / 1000);

             } catch (Throwable e) {
                 System.out.println("foobar :(");
                 e.printStackTrace();
             }
         }

    }
}
