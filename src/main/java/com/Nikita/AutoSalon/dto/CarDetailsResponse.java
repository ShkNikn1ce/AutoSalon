package com.Nikita.AutoSalon.dto;

import com.Nikita.AutoSalon.enums.CarStatus;

import java.math.BigDecimal;

public class CarDetailsResponse {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private double mileage;
    private BigDecimal price;
    private CarStatus status;
}
