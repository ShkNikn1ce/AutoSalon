package com.Nikita.AutoSalon.dto;

import com.Nikita.AutoSalon.enums.CarStatus;

import java.math.BigDecimal;

public class CarResponse {
    private String brand;
    private String model;
    private int year;
    private BigDecimal price;
    private CarStatus status;
}
