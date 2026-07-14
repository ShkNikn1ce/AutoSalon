package com.Nikita.AutoSalon.mapper;

import com.Nikita.AutoSalon.dto.response.CarResponse;
import com.Nikita.AutoSalon.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarResponse toResponse(Car request){

        CarResponse response = new CarResponse();

        response.setBrand(request.getBrand().getName());
        response.setModel(request.getModel());
        response.setYear(request.getYear());
        response.setPrice(request.getPrice());
        response.setMileage(request.getMileage());
        response.setStatus(request.getStatus());

        return response;
    }
}
