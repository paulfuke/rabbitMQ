package com.dongpo.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

/**
 * @className RabbitMQUtil
 * @Description: TODO
 * @Author dongpo
 * @Date 2020/7/11
 * @Version V1.0
 **/
public class RabbitMQUtil {
    public static final Integer port;
    public static final String host;
    public static final String virtual;
    public static final String username;
    public static final String password;
    private static final ConnectionFactory connectionFactory;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        port = Integer.parseInt(bundle.getString("rabbitmq.port"));
        host = bundle.getString("rabbitmq.host");
        virtual = bundle.getString("rabbitmq.virtual");
        username = bundle.getString("rabbitmq.username");
        password = bundle.getString("rabbitmq.password");
         connectionFactory = new ConnectionFactory();
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接工厂
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtual);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        //创建
        return connectionFactory.newConnection();
    }
}
