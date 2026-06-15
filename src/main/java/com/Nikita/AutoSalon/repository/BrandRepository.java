package com.Nikita.AutoSalon.repository;

import com.Nikita.AutoSalon.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
}
