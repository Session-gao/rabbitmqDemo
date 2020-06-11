package com.example.rabbitmqdemo.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RecV {
    public final static String QUEUE_NAME="queue1";
    static Log log = LogFactory.getLog(RecV.class);
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.112.128");


        Connection connection = factory.newConnection();
        Channel ch = connection.createChannel();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1",new String("the one value"));
        map.put("2",new String("the two value"));
        map.put("3",new String("the three value"));
        ch.queueDeclare(QUEUE_NAME,false,false,false,map);
        log.info("[*] Waiting for messages,To exit precc Ctrl+C");
        //消息回调
        DeliverCallback deliverCallback =(consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            log.info("[X] Received'"+message+"'");
        };
        ch.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag->{});

    }
}
