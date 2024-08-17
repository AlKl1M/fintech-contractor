package com.alkl1m.contractor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class MQConfiguration {
    public static final String UPDATE_MAIN_BORROWER_QUEUE = "deals_update_main_borrower_queue";
    public static final String UPDATE_MAIN_BORROWER_EXC = "deals_update_main_borrower_exchange";
    public static final String UPDATE_MAIN_BORROWER_ROUTING_KEY = "deals_update_main_borrower_routing_key";

    public static final String CONTRACTORS_EXCHANGE = "contractors_contractor_exchange";

    public static final String DEAL_CONTRACTOR_QUEUE = "deals_contractor_queue";
    public static final String DEAL_CONTRACTOR_DLQ = "deals_dead_contractor_queue";
    public static final String DLX_EXCHANGE_MESSAGES = "deals_dead_contractor_exchange";

    @Bean
    RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory, Jackson2ObjectMapperBuilder builder) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter(builder));
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build().registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    Queue mainBorrowerQueue() {
        return QueueBuilder.durable(UPDATE_MAIN_BORROWER_QUEUE)
                .build();
    }

    @Bean
    Queue contractorQueue() {
        return QueueBuilder.durable(DEAL_CONTRACTOR_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE_MESSAGES)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAL_CONTRACTOR_DLQ)
                .withArgument("x-dead-letter-exchange", CONTRACTORS_EXCHANGE)
                .withArgument("x-message-ttl", 300000)
                .build();
    }

    @Bean
    DirectExchange mainBorrowerExchange() {
        return new DirectExchange(UPDATE_MAIN_BORROWER_EXC);
    }

    @Bean
    DirectExchange contractorExchange() {
        return new DirectExchange(CONTRACTORS_EXCHANGE);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLX_EXCHANGE_MESSAGES);
    }

    @Bean
    Binding mainBorrowerBinding() {
        return BindingBuilder.bind(mainBorrowerQueue()).to(mainBorrowerExchange()).with(UPDATE_MAIN_BORROWER_ROUTING_KEY);
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(contractorQueue()).to(contractorExchange()).with(DEAL_CONTRACTOR_QUEUE);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

}