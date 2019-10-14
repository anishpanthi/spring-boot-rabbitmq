package com.app.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anish Panthi
 */
@Configuration
@Slf4j
public class MessageRunner implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.topic-exchange-name:app-exchange}")
    private String topicExchangeName;

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, "app.rabbitmq.msg", "Bingo! You did it.");
    }

    @Autowired
    public MessageRunner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
