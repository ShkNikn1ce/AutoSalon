package com.Nikita.AutoSalon.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleCreateEvent {
    private Long saleId;
    private Long carId;
    private String customerEmail;
}
