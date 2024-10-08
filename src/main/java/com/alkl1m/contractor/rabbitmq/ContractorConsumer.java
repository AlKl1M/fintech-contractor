package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.service.ContractorService;
import com.alkl1m.contractor.web.payload.MainBorrowerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Класс-потребитель для обработки сообщений о подрядчиках.
 *
 * @author alkl1m
 */
@Component
@RequiredArgsConstructor
public class ContractorConsumer {

    private final ContractorService contractorService;

    /**
     * Обрабатывает входящие сообщения об изменении основного заемщика.
     *
     * @param msg сообщение, содержащее данные о запросе изменения основного заемщика.
     */
    @RabbitListener(id = MQConfiguration.CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE,
            queues = MQConfiguration.CONTRACTOR_UPDATE_MAIN_BORROWER_QUEUE)
    public void receiveMessage(MainBorrowerRequest msg) {
        contractorService.changeMainBorrower(msg.contractorId(), msg.main());
    }
}