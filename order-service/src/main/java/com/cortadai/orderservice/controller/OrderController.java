package com.cortadai.orderservice.controller;

import com.cortadai.orderservice.dto.OrderDto;
import com.cortadai.orderservice.dto.OrderEventDto;
import com.cortadai.orderservice.publisher.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody OrderDto orderDto){
        orderDto.setOrderId((UUID.randomUUID().toString()));
        OrderEventDto eventDto = new OrderEventDto();
        eventDto.setStatus("PENDING");
        eventDto.setMessage("Order is in pending status");
        eventDto.setOrder(orderDto);
        producer.sendMessage(eventDto);
        return "Order sent to the RabbitMQ ...";
    }

}
