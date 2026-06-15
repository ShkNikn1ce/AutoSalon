package com.Nikita.AutoSalon.dto;
import java.math.BigDecimal;

public class CreateCarRequest {
    private Long brandId;
    private String model;
    private String vin;
    private int year;
    private double mileage;
    private BigDecimal price;
}
