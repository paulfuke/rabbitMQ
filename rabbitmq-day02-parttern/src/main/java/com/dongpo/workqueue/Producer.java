package com.dongpo.workqueue;

import com.dongpo.utils.RabbitMQUtil;
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
    public static final String QUEUE_NAME = "work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        /*
           参数1：队列名称
           参数2：是否持久化队列
           参数3：是否独占本次连接
           参数4：对否不使用的时候删除队列
           参数5：其他参数
         */
        channel.queueDeclare(QUEUE_NAME,true, false, false,null);
        IntStream.range(1, 30).forEach(i->{
            String message = "测试"+ i;
            try {
                channel.basicPublish("", QUEUE_NAME, null,message.getBytes());
            } catch (IOException e) { }
            System.out.println("发送消息 "+message);
        });
        channel.close();
        connection.close();
    }
}
