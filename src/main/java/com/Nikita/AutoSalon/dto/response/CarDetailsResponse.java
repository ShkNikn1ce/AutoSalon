package com.Nikita.AutoSalon.dto.response;

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
