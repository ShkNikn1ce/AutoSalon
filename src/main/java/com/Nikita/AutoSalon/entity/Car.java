package com.Nikita.AutoSalon.entity;
import com.Nikita.AutoSalon.enums.CarStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cars")//Таблица сущностей "Автомобиль"
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private String model;

    @Column(unique = true,nullable = false)
    private String vin;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private double mileage;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    public Car(){}

    public Car(long id, Brand brand, String model, String vin, int year, double mileage, BigDecimal price, CarStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
    }
}
