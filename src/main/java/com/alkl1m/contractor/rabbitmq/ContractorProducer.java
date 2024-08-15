package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.web.payload.UpdateContractorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractorProducer {
    private final RabbitTemplate template;
    @Value("${deal.dealExchange}")
    private String exchange;
    @Value("${deal.dealUpdateRoutingKey}")
    private String updateRoutingKey;
    @Value("${deal.dealCreateRoutingKey}")
    private String createRoutingKey;

    public void sendUpdateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(exchange, updateRoutingKey, msg);
    }

    public void sendCreateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(exchange, createRoutingKey, msg);
    }
}
