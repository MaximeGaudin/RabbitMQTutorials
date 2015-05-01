package com.mgaudin.sandbox.rabbitmqtutorial2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Tutorial2ProducerApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Tutorial2ProducerApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
    }
}
