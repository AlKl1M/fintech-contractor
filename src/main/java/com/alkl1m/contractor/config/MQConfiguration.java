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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class MQConfiguration {

    public static final String CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE = "deals_update_main_borrower_queue";
    public static final String DEALS_CONTRACTOR_NEW_DATA_QUEUE = "deals_contractor_queue";
    public static final String DEALS_CONTRACTOR_UNDELIVERED_QUEUE = "undelivered_queue";
    public static final String DEALS_CONTRACTOR_DLQ = "deals_dead_contractor_queue";

    public static final String DEALS_CONTRACTOR_DLX = "deals_dead_contractor_exchange";
    public static final String DEALS_UPDATE_MAIN_BORROWER_EXC = "deals_update_main_borrower_exchange";
    public static final String DEALS_CONTRACTORS_EXC = "contractors_contractor_exchange";

    @Value("${spring.rabbitmq.millisToResend}")
    private int millisToResend;

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
        return QueueBuilder.durable(CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE)
                .build();
    }

    @Bean
    Queue contractorQueue() {
        return QueueBuilder.durable(DEALS_CONTRACTOR_NEW_DATA_QUEUE)
                .withArgument("x-dead-letter-exchange", DEALS_CONTRACTOR_DLX)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(DEALS_CONTRACTOR_DLQ)
                .withArgument("x-dead-letter-exchange", DEALS_CONTRACTORS_EXC)
                .withArgument("x-message-ttl", millisToResend)
                .build();
    }

    @Bean
    Queue undeliveredQueue() {
        return QueueBuilder.durable(DEALS_CONTRACTOR_UNDELIVERED_QUEUE)
                .build();
    }

    @Bean
    DirectExchange mainBorrowerExchange() {
        return new DirectExchange(DEALS_UPDATE_MAIN_BORROWER_EXC);
    }

    @Bean
    DirectExchange contractorExchange() {
        return new DirectExchange(DEALS_CONTRACTORS_EXC);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DEALS_CONTRACTOR_DLX);
    }

    @Bean
    Binding mainBorrowerBinding() {
        return BindingBuilder.bind(mainBorrowerQueue()).to(mainBorrowerExchange()).with(CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE);
    }

    @Bean
    Binding dealsContractorNewData() {
        return BindingBuilder.bind(contractorQueue()).to(contractorExchange()).with(DEALS_CONTRACTOR_NEW_DATA_QUEUE);
    }
    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    Binding undeliveredBinding() {
        return BindingBuilder.bind(undeliveredQueue()).to(contractorExchange()).with(DEALS_CONTRACTOR_UNDELIVERED_QUEUE);
    }

}
