package com.ms.ecommerce.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.order.exchange}")
    private String orderExchange;

    @Value("${rabbitmq.order.created.queue}")
    private String orderCreatedQueue;

    @Value("${rabbitmq.order.updated.queue}")
    private String orderUpdatedQueue;

    //Create the queue
    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(orderCreatedQueue, true);
    }

    @Bean
    public Queue orderUpdatedQueue() {
        return new Queue(orderUpdatedQueue, true);
    }

    @Bean
    public DirectExchange creteDirectOrderExchange() {
        return new DirectExchange(orderExchange);
    }

    @Bean
    public Binding bindingOrderCreatedQueue(DirectExchange orderExchange, Queue orderCreatedQueue){
        return BindingBuilder.bind(orderCreatedQueue)
                .to(orderExchange).with("order.created");
    }

    @Bean
    public Binding bindingOrderUpdatedQueue(DirectExchange orderExchange, Queue orderUpdatedQueue){
        return BindingBuilder.bind(orderUpdatedQueue)
                .to(orderExchange).with("order-updated");
    }

    //Create the admin
    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

}
