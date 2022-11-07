package com.licw.springboot_rabbitmq.delayedQueue.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 * @author licw
 * @version V1.0.0
 * @date 2022/11/6 13:34
 */
@Configuration
public class DelayedQueueConfig {
    // 普通交换机名称
    private static final String X_EXCHANGE = "X";
    // 死信交换机名称
    private static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    // 普通队列名称
    private static final String QUEUE_A = "QA";
    private static final String QUEUE_B = "QB";
    private static final String QUEUE_C = "QC";
    // 死信队列名称
    private static final String DEAD_LETTER_QUEUE_D = "QD";

    @Bean("xExchange")
    public DirectExchange normalExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange deadLetterExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean("queueA")
    public Queue queueA(){

        return QueueBuilder.durable(QUEUE_A)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .ttl(10000)
                .build();
    }

    @Bean("queueB")
    public Queue queueB(){

        return QueueBuilder.durable(QUEUE_B)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .ttl(40000)
                .build();
    }

    // QA和QB在声明队列的时候指定TTL具有局限性,
    // QC在声明时不指定TTL，转由生产者指定TTL，因此它更加通用，可以延迟任意时间的消息
    @Bean("queueC")
    public Queue queueC(){

        return QueueBuilder.durable(QUEUE_C)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }
    //声明队列 C 绑定 X 交换机
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }


    // 死信队列
    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_D).build();
    }

    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("queueD") Queue queueD,
                                        @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
