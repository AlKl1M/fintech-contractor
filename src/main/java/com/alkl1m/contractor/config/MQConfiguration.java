package com.alkl1m.contractor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class MQConfiguration {
    @Value("${deal.contractorQueue}")
    private String queueName;
    @Value("${deal.contractorExchange}")
    private String exchangeName;
    @Value("${deal.contractorRoutingKey}")
    private String contractorRoutingKey;

    @Bean
    RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory, Jackson2ObjectMapperBuilder builder) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(builder));
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer containerFactoryConfigurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        containerFactoryConfigurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build().registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    Queue mainBorrowerQueue() {
        return QueueBuilder.durable(queueName)
                .build();
    }

    @Bean
    DirectExchange contractorExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding orderBinding(Queue mainBorrowerQueue, DirectExchange contractorExchange) {
        return BindingBuilder.bind(mainBorrowerQueue).to(contractorExchange).with(contractorRoutingKey);
    }

}