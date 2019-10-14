package com.app.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Anish Panthi
 */
@Component
@Slf4j
public class MessageReceiver {

    public void receiveMessage(String message){
        log.info("Received Message:: {}", message);
    }
}
