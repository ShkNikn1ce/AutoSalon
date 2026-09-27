package com.Nikita.AutoSalon.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCreateEvent {
    private Long saleId;
    private Long carId;
    private String customerEmail;
}
