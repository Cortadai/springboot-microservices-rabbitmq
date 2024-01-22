package com.cortadai.stockservice.consumer;

import com.cortadai.stockservice.dto.OrderEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.order.name}")
    public void consume(OrderEventDto eventDto){
        LOGGER.info(String.format("Order event received in stock service => %s", eventDto.toString()));
        // save order event data in a database...
    }


}
