package com.app.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anish Panthi
 */
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.topic-exchange-name:app-exchange}")
    private String topicExchangeName;

    @Value("${spring.rabbitmq.queue-name:app-queue}")
    private String queueName;

    private MessageReceiver messageReceiver;

    @Bean
    public Queue queue(){
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with("app.rabbitmq.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(messageReceiver, "receiveMessage");
    }

    @Autowired
    public RabbitMQConfig(MessageReceiver messageReceiver){
        this.messageReceiver = messageReceiver;
    }
}
