package com.licw.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 创建channel的工具类
 *
 * @author licw
 * @version V1.0.0
 * @date 2022/11/4 13:19
 */
public class RabbitUtils {

    private static String Host = "182.92.234.71";
    private static String Username = "licw";
    private static String Password = "lcw810905";

    //得到一个连接的 channel
    public static Channel getChannel() throws Exception{
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Host);
        factory.setUsername(Username);
        factory.setPassword(Password);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}