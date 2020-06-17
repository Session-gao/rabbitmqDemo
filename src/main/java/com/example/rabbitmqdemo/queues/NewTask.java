package com.example.rabbitmqdemo.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class NewTask {
    private static final Log log = LogFactory.getLog(NewTask.class);
    private static final String TASK_QUEUE_NAME = "tase_queue";
    public static void main(String[] args){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.112.128");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //向队列中发送20条消息
            for(int i =0;i<20;i++){
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String message = "THE Message"+i;
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            log.info("[x] Sent '"+message+"'");
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
