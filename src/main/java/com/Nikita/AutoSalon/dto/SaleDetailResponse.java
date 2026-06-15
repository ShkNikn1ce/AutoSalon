package com.Nikita.AutoSalon.dto;

import java.math.BigDecimal;

public class SaleDetailResponse {
    private Long id;

    private Long carId;
    private String brand;
    private String model;
    private int year;
    private double mileage;

    private Long managerId;
    private String managerFirstName;
    private String managerLastName;

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerEmail;

    private BigDecimal finalPrice;
}
