package com.Nikita.AutoSalon.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateCarRequest {
    private String model;
    private String vin;
    private int year;
    private double mileage;
    private BigDecimal price;
}
