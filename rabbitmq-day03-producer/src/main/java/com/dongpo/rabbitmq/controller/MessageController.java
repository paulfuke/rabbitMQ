package com.dongpo.rabbitmq.controller;

import com.dongpo.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className MessageController
 * @Description: TODO
 * @Author dongpo
 * @Date 2020/7/12
 * @Version V1.0
 **/
@RestController
public class MessageController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String send(@RequestParam(value = "key") String key,
                       @RequestParam(value = "msg") String msg){
        try {
            /**
             * 参数1：交换机
             * 参数2：routingkey
             * 参数3：发送的消息
             */
            rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE,key,msg);
            return "发送成功";
        }catch (Exception e){
            e.printStackTrace();
            return "发送失败";
        }
    }
}
