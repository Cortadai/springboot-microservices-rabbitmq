package com.cortadai.orderservice.publisher;

import com.cortadai.orderservice.dto.OrderEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.exchange.order.name}")
    private String orderExchange;

    @Value("${rabbitmq.key.order.name}")
    private String orderKey;

    @Value("${rabbitmq.key.email.name}")
    private String emailKey;

    private RabbitTemplate template;

    public OrderProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(OrderEventDto orderEventDto){
        LOGGER.info(String.format("Order event sent to RabbitMQ => %s", orderEventDto.toString()));
        // send an order event to order queue
        template.convertAndSend(orderExchange, orderKey, orderEventDto);
        // send an order event to email queue
        template.convertAndSend(orderExchange, emailKey, orderEventDto);
    }

}
