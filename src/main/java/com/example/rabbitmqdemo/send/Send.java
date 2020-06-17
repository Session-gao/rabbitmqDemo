package com.example.rabbitmqdemo.send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Send {
    public final static String QUEUE_NAME="hello";
    static Log log = LogFactory.getLog(RecV.class);
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.112.128");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5621);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String message = "This is my first message";
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
        log.info("[x] send '"+message+"'");
    }
}
