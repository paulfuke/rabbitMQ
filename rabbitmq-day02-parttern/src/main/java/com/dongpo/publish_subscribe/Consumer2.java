package com.dongpo.publish_subscribe;

import com.dongpo.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @className Consumer1
 * @Description: TODO
 * @Author dongpo
 * @Date 2020/7/11
 * @Version V1.0
 **/
public class Consumer2 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = RabbitMQUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE,BuiltinExchangeType.FANOUT);

        //声明或者创建队列
        channel.queueDeclare(Producer.FANOUT_QUEUE_2,true,false,false,null);

        //绑定交换机
        channel.queueBind(Producer.FANOUT_QUEUE_2,Producer.FANOUT_EXCHANGE,"");
        //监听消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * @param consumerTag 消息者标签
             * @param envelope 消息内容，从中可以获取消息id，消息的routingkey,交换机（exchange），消息和重转标记
             * @param properties 消息属性
             * @param body 消息
             * @throws IOException 抛出异常
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("=======消费者1开始=====");
                System.out.println("接收到的消息："+new String(body,"UTF-8"));
                System.out.println("========消费者1结束===========");
            }
        };
        /**
         * 参数1：队列名称
         * 参数2：是否自动确认，mq自动确认消息后会自动删除，否则需要手动确认
         * 参数3：
         */
        channel.basicConsume(Producer.FANOUT_QUEUE_2,true,consumer);
        //不关闭资源--一直在监听状态

    }
}
