//package com.licw.six;
//
//import com.licw.utils.RabbitUtils;
//import com.rabbitmq.client.BuiltinExchangeType;
//import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.DeliverCallback;
//
//public class ReceiveLogsDirect01 {
//    private static final String EXCHANGE_NAME = "direct_logs";
//
//    public static void main(String[] argv) throws Exception {
//        //获取信道
//        Channel channel = RabbitUtils.getChannel();
////        声明一个交换机
//        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
//
//        String queueName = "console";
//        // 声明一个队列
//        channel.queueDeclare(queueName, false, false, false, null);
//        // 绑定
//        channel.queueBind(queueName, EXCHANGE_NAME, "info");
//
//        System.out.println("等待接收消息,打印到屏幕上........... ");
//
//        DeliverCallback deliverCallback = (consumerTag, message)->{
//
//        };
//
//        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
//        });
//    }
//}