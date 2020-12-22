package org.yanwen.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.yanwen"})
public class ConsumerApplication {
    public static void main(String[] args){
        ApplicationContext app = SpringApplication.run(ConsumerApplication.class, args);
    }
}