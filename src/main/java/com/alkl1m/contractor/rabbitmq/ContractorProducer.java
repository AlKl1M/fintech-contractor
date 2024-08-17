package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.web.payload.UpdateContractorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractorProducer {
    private final RabbitTemplate template;

    public void sendUpdateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(MQConfiguration.CONTRACTORS_EXCHANGE, MQConfiguration.DEAL_CONTRACTOR_QUEUE, msg);
    }

    public void sendCreateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(MQConfiguration.CONTRACTORS_EXCHANGE, MQConfiguration.DEAL_CONTRACTOR_QUEUE, msg);
    }

}
