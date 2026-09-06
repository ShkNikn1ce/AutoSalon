package com.Nikita.AutoSalon.kafka.producer;

import com.Nikita.AutoSalon.kafka.dto.SaleCreateEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SaleEventProducer {
    private final KafkaTemplate<String, SaleCreateEvent> kafkaTemplate;

    public void send(SaleCreateEvent event){
        kafkaTemplate.send("sale-created", event);
    }

}
