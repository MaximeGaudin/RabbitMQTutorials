package com.mgaudin.sandbox.rabbitmqtutorial2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class Tutorial2WorkerApplication implements CommandLineRunner {
    @Autowired
    private Receiver receiver;

    public static void main(String[] args) {
        SpringApplication.run(Tutorial2WorkerApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        receiver.receive();
    }
}
