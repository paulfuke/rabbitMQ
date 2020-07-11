package com.dongpo.publish_subscribe;

import com.dongpo.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;


/**
 * @className provider
 * @Description: TODO
 * @Author dongpo
 * @Date 2020/7/11
 * @Version V1.0
 **/
public class Producer {
    public static final String FANOUT_EXCHANGE = "fanout_exchange";
    public static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    public static final String FANOUT_QUEUE_2 = "fanout_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        /*
           参数1：队列名称
           参数2：是否持久化队列
           参数3：是否独占本次连接
           参数4：对否不使用的时候删除队列
           参数5：其他参数
         */
        channel.queueDeclare(FANOUT_QUEUE_1,true, false, false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true, false, false,null);

        //队列绑定交换机
        channel.queueBind(FANOUT_QUEUE_1, FANOUT_EXCHANGE, "");
        channel.queueBind(FANOUT_QUEUE_2, FANOUT_EXCHANGE, "");

        IntStream.range(1, 30).forEach(i->{
            String message = "fanout_消息-"+ i;
            try {
                channel.basicPublish(FANOUT_EXCHANGE,"", null,message.getBytes());
            } catch (IOException e) { }
            System.out.println("发送消息 "+message);
        });
        channel.close();
        connection.close();
    }
}
