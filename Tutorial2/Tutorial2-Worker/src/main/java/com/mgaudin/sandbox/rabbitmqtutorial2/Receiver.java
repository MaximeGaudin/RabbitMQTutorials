package com.mgaudin.sandbox.rabbitmqtutorial2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class Receiver implements DisposableBean {
    public static final String QUEUE_NAME = "PING";
    private final Channel channel;
    private final Connection connection;

    public Receiver() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

        this.channel.queueDeclarePassive(QUEUE_NAME);
    }

    public void receive() throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            log.info(" [x] Processing '" + message + "'");
            Thread.sleep(100);
            log.info(" [x] Processing of " + message + " done");

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    @Override
    public void destroy() throws Exception {
        this.channel.close();
        this.connection.close();
    }
}
