package com.Nikita.AutoSalon.dto;

import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;

public class PurchaseRequestResponse {
    private Long id;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private Long carId;
    private String brand;
    private String model;
    private int year;
    private double mileage;
    private PurchaseRequestStatus status;
    private String comment;
}
