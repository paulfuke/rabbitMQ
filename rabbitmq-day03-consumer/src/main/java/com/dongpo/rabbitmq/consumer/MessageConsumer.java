package com.dongpo.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className MessageConsumer
 * @Description: TODO
 * @Author dongpo
 * @Date 2020/7/12
 * @Version V1.0
 **/
@Component
public class MessageConsumer {
    @RabbitListener(queues = "boot_queue")
    public void consume(String message){
        System.out.println("消费消息  "+message);
    }
}
