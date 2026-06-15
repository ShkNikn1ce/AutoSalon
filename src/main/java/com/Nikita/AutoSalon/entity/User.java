package com.Nikita.AutoSalon.entity;
import com.Nikita.AutoSalon.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="users")
public class User { //Таблица сущностей "Пользователей системы"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public User(){}

    public User(long id, String firstName, String lastName, String email, String phone, String password, Roles role, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }
}
