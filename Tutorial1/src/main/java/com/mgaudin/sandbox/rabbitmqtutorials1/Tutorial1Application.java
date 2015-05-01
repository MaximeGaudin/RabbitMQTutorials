package com.mgaudin.sandbox.rabbitmqtutorials1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class Tutorial1Application implements CommandLineRunner {
    @Autowired
    private Receiver receiver;

    public static void main(String[] args) {
        SpringApplication.run(Tutorial1Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        receiver.receive();
    }
}
