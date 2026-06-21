package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public boolean existsBrand(String brand){
        return brandRepository.existsByName(brand);
    }
}
