package com.Nikita.AutoSalon.kafka.producer;

import com.Nikita.AutoSalon.kafka.KafkaTopics;
import com.Nikita.AutoSalon.kafka.dto.SaleCreateEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class SaleEventProducer {
    private final KafkaTemplate<String, SaleCreateEvent> kafkaTemplate;

    public void send(SaleCreateEvent event) {
        kafkaTemplate.send(KafkaTopics.SALE_CREATED, String.valueOf(event.getSaleId()), event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Не удалось отправить событие продажи {}", event.getSaleId(), exception);
                        return;
                    }
                    log.info("Событие продажи {} отправлено в топик {}",
                            event.getSaleId(),
                            result.getRecordMetadata().topic());
                });
    }
}
