package com.Nikita.AutoSalon.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SaleDetailResponse {
    private Long id;

    private String vin;
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

    private Long purchaseRequestId;

    private BigDecimal finalPrice;
    private LocalDateTime saleDate;
}
