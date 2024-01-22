package com.cortadai.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.queue.order.name}")
    private String orderQueue;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.order.name}")
    private String orderExchange;

    @Value("${rabbitmq.key.order.name}")
    private String orderKey;

    @Value("${rabbitmq.key.email.name}")
    private String emailKey;

    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue);
    }

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(orderExchange);
    }

    @Bean
    public Binding oderBinding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(orderKey);
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder
                .bind(emailQueue())
                .to(orderExchange())
                .with(emailKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter());
        return template;
    }

}
