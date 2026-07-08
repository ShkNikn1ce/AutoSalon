package com.Nikita.AutoSalon.mapper;

import com.Nikita.AutoSalon.dto.BrandResponse;
import com.Nikita.AutoSalon.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public BrandResponse toResponse(Brand request) {
        BrandResponse response = new BrandResponse();

        response.setBrandId(request.getId());
        response.setBrand(request.getName());

        return response;
    }
}
