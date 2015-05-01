package com.mgaudin.sandbox.rabbitmqtutorials1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Producer implements DisposableBean {
    public static final String QUEUE_NAME = "PING";
    private final Channel channel;
    private final Connection connection;

    public Producer() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    @Scheduled(fixedDelay = 100)
    public void sendMessage() throws IOException {
        String message = "Hello world";
        channel.basicPublish("", "PING", null, message.getBytes());
    }

    @Override
    public void destroy() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
