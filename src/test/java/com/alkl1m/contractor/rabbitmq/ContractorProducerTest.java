package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.web.payload.UpdateContractorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.ZonedDateTime;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@SpringBootTest
class ContractorProducerTest {

    @Container
    static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management")
            .withVhost("vhost");

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12")
            .withReuse(true);

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitListenerEndpointRegistry registry;
    @Autowired
    RabbitAdmin rabbitAdmin;

    @Autowired
    ContractorProducer contractorProducer;

    @Test
    void testSendCreateMessage_withValidData_savesMessageToQueue() {
        UpdateContractorMessage updateContractorMessage = new UpdateContractorMessage("100", "newName", "101010101", ZonedDateTime.now().toString(), "1");
        contractorProducer.sendCreateMessage(updateContractorMessage);

        await().atMost(10, SECONDS).untilAsserted(() -> {
            UpdateContractorMessage receivedMessage = (UpdateContractorMessage) rabbitTemplate.receiveAndConvert(MQConfiguration.DEALS_CONTRACTOR_NEW_DATA_QUEUE);
            assertNotNull(receivedMessage);
        });
    }

    @Test
    void testSendUpdateMessage_withValidData_savesMessageToQueue() {
        UpdateContractorMessage updateContractorMessage = new UpdateContractorMessage("1", "newName", "101010101", ZonedDateTime.now().toString(), "1");
        contractorProducer.sendUpdateMessage(updateContractorMessage);

        await().atMost(10, SECONDS).untilAsserted(() -> {
            UpdateContractorMessage receivedMessage = (UpdateContractorMessage) rabbitTemplate.receiveAndConvert(MQConfiguration.DEALS_CONTRACTOR_NEW_DATA_QUEUE);
            assertNotNull(receivedMessage);
        });
    }

}