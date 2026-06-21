package com.Nikita.AutoSalon.entity;

import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "purchase_requests")
public class PurchaseRequest { //Таблица заявок клиентов на покупку автомобиля
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseRequestStatus status;


    public PurchaseRequest(){}

    public PurchaseRequest(long id, User customer, Car car, LocalDateTime createdAt, String comment, PurchaseRequestStatus status) {
        this.id = id;
        this.customer = customer;
        this.car = car;
        this.createdAt = createdAt;
        this.comment = comment;
        this.status = status;
    }
}
