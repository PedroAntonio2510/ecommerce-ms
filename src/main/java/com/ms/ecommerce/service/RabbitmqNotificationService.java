package com.ms.ecommerce.service;

import com.ms.ecommerce.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitmqNotificationService {

    private RabbitTemplate rabbitTemplate;

    public void notificate(Order order, String exchange){
        rabbitTemplate.convertAndSend(exchange, "", order);
    }
}
