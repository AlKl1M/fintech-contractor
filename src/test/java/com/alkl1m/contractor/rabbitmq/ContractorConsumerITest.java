package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.repository.ContractorRepository;
import com.alkl1m.contractor.web.payload.MainBorrowerRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
class ContractorConsumerITest {

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
    ContractorRepository contractorRepository;

    @AfterEach
    void tearDown() {
        contractorRepository.deleteAll();
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testMainBorrowerConsume_withValidData_changesMainBorrower() {
        MainBorrowerRequest mainBorrowerRequest = new MainBorrowerRequest("1", true);
        rabbitTemplate.convertAndSend(MQConfiguration.CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE, mainBorrowerRequest);

        await().atMost(10, SECONDS).untilAsserted(() -> {
            assertEquals(contractorRepository.findById(mainBorrowerRequest.contractorId()).get().isActiveMainBorrower(), mainBorrowerRequest.main());
        });
    }

    @Test
    @Sql("/sql/contractors.sql")
    void testMainBorrowerConsume_withPausedListener_changesMainBorrowerAfterPause() {
        registry.stop();
        MainBorrowerRequest mainBorrowerRequest = new MainBorrowerRequest("1", true);
        rabbitTemplate.convertAndSend(MQConfiguration.CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE, mainBorrowerRequest);

        registry.getListenerContainer(MQConfiguration.CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE).start();

        await().atMost(10, SECONDS).untilAsserted(() -> {
            assertEquals(contractorRepository.findById(mainBorrowerRequest.contractorId()).get().isActiveMainBorrower(), mainBorrowerRequest.main());
        });
    }

}
