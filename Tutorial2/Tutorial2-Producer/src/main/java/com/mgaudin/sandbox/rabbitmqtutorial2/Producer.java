package com.mgaudin.sandbox.rabbitmqtutorial2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Producer implements DisposableBean {
    public static final String QUEUE_NAME = "PING";
    private final Channel channel;
    private final Connection connection;

    private Integer counter = 0;

    public Producer() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        this.channel.basicQos(1);
    }

    @Scheduled(fixedDelay = 2000)
    public void sendMessage() throws IOException {
        String message = "Task[" + counter.toString() + "]";

        log.info("Sending " + message + " ...");
        channel.basicPublish("", "PING", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        counter += 1;
    }

    @Override
    public void destroy() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
