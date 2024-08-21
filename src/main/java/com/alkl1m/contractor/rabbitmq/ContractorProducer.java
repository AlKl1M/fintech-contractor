package com.alkl1m.contractor.rabbitmq;

import com.alkl1m.contractor.config.MQConfiguration;
import com.alkl1m.contractor.web.payload.UpdateContractorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс-производитель для отправки сообщений о контрагентах.
 *
 * @author alkl1m
 */
@Component
@RequiredArgsConstructor
public class ContractorProducer {

    private final RabbitTemplate template;

    /**
     * Отправляет сообщение об обновлении подрядчика в очередь RabbitMQ.
     *
     * @param msg сообщение, содержащее данные об обновлении контрагента.
     */
    public void sendUpdateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(MQConfiguration.DEALS_CONTRACTORS_EXC,
                MQConfiguration.DEALS_CONTRACTOR_NEW_DATA_QUEUE,
                msg);
    }

    /**
     * Отправляет сообщение о создании нового контрагента в очередь RabbitMQ.
     *
     * @param msg сообщение, содержащее данные о новом контрагенте.
     */
    public void sendCreateMessage(UpdateContractorMessage msg) {
        template.convertAndSend(MQConfiguration.DEALS_CONTRACTORS_EXC,
                MQConfiguration.DEALS_CONTRACTOR_NEW_DATA_QUEUE,
                msg);
    }
}