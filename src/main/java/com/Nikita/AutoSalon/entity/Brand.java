package com.Nikita.AutoSalon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="brands")
public class Brand {//Таблица с марками автомобилей
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    public Brand(){}

    public Brand(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
