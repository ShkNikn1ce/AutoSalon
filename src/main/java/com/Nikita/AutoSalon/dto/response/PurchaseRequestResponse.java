package com.Nikita.AutoSalon.dto;

import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseRequestResponse {
    private Long id;

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    private Long managerId;
    private String managerFirstName;
    private String managerLastName;

    private Long carId;
    private String brand;
    private String model;
    private int year;
    private double mileage;

    private String comment;

    private PurchaseRequestStatus status;

    private String rejectReason;

    private LocalDateTime CreatedAt;
}
