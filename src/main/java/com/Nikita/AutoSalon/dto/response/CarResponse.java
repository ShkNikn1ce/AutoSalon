package com.Nikita.AutoSalon.dto;
import com.Nikita.AutoSalon.enums.CarStatus;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private double mileage;
    private BigDecimal price;
    private CarStatus status;
}
