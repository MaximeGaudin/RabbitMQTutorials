package com.mgaudin.sandbox.rabbitmqtutorials1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@DependsOn("producer")
public class Receiver implements DisposableBean {
    public static final String QUEUE_NAME = "PING";
    private final Channel channel;
    private final Connection connection;

    public Receiver() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void receive() throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info(" [x] Received '" + message + "'");
        }
    }

    @Override
    public void destroy() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
