package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.MainBorrowerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractorConsumer {
    private final ContractorService contractorService;

    @RabbitListener(queues = "${deal.contractorQueue}")
    public void receiveMessage(MainBorrowerRequest msg) {
        contractorService.changeMainBorrower(msg.contractorId(), msg.main());
    }
}
