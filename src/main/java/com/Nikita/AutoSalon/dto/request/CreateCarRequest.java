package com.Nikita.AutoSalon.dto.request;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateCarRequest {
    private Long brandId;
    private String model;
    private String vin;
    private int year;
    private double mileage;
    private BigDecimal price;
}
