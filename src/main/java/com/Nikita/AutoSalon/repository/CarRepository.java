package com.Nikita.AutoSalon.repository;
import com.Nikita.AutoSalon.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CarRepository extends JpaRepository<Car, Long> {
    boolean existsByVin(String vin);
    Optional<Car> findByVin(String vin);
}
