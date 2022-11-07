package com.licw.springboot_rabbitmq.publishAndConfirmMode.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback , RabbitTemplate.ReturnsCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 依赖注入 rabbitTemplate 之后再设置它的回调对象
    // PostConstruct注释用于需要在依赖注入完成后执行的方法，以执行任何初始化。
    // 必须在类投入服务之前调用此方法。所有支持依赖注入的类都必须支持此注释。
    // 即使类没有请求注入任何资源，也必须调用用PostConstruct注释的方法。
    // 只能使用此注释对一个方法进行注释。
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机不管是否收到消息的一个回调方法
     * CorrelationData: 消息相关数据
     * ack:交换机是否收到消息
     * cause: 失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到 id 为:{}的消息", id);
        } else {
            log.info("交换机还未收到 id 为:{}消息,由于原因:{}", id, cause);
        }
    }

    // 可以在当消息传递过程中不可达目的地时将消息返回给生产者
    // 只有不可达目的地的时候 才进行回退

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息{}被交换机{}退回， 退回原因：{}， 路由key：{}",
                new String(returnedMessage.getMessage().getBody()),
                returnedMessage.getExchange(),
                returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey());
    }

}
