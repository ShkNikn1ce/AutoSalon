package com.Nikita.AutoSalon.kafka.consumer;

import com.Nikita.AutoSalon.kafka.KafkaTopics;
import com.Nikita.AutoSalon.kafka.dto.SaleCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaleCreatedConsumer {

    @KafkaListener(topics = KafkaTopics.SALE_CREATED, groupId = "autosalon-sales")
    public void handle(SaleCreateEvent event) {
        log.info("Получено событие продажи: saleId={}, carId={}, customerEmail={}",
                event.getSaleId(), event.getCarId(), event.getCustomerEmail());
    }
}
