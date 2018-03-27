package com.jackzhang.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  rabbitmq连接工具类
 * Created by Jack on 2018/3/27.
 */
public class ConnectionUtil {
    public static Connection getConnectionMq() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.60.129");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jackzhang");
        connectionFactory.setPassword("jackzhang");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
