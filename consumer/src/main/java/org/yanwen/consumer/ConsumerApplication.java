package org.yanwen.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.yanwen.consumer.service.SQSMessageService;

@SpringBootApplication(scanBasePackages = {"org.yanwen.consumer"})
public class ConsumerApplication {
    public static void main(String[] args){
//        SpringApplication.run(ConsumerApplication.class, args);
        ConfigurableApplicationContext app = SpringApplication.run(ConsumerApplication.class, args);
        SQSMessageService messageService = app.getBean(SQSMessageService.class);
        messageService.receiveMessage();
    }
}