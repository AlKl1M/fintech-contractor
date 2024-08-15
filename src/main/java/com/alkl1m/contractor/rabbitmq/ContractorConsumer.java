package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.MainBorrowerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractorConsumer {
    private final ContractorService contractorService;

    @RabbitListener(queues = MQConfiguration.UPDATE_MAIN_BORROWER)
    public void receiveMessage(MainBorrowerRequest msg) {
        contractorService.changeMainBorrower(msg.contractorId(), msg.main());
    }
}
