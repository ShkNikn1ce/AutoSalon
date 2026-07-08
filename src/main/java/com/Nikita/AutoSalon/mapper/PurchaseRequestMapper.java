package com.Nikita.AutoSalon.mapper;

import com.Nikita.AutoSalon.dto.response.PurchaseRequestResponse;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PurchaseRequestMapper {
    public PurchaseRequestResponse toResponse(PurchaseRequest request) {

        PurchaseRequestResponse response = new PurchaseRequestResponse();

        User customer = request.getCustomer();

        response.setId(request.getId());
        response.setCustomerId(customer.getId());
        response.setCustomerFirstName(customer.getFirstName());
        response.setCustomerLastName(customer.getLastName());

        User manager = request.getManager();

        if (manager != null) {
            response.setManagerId(manager.getId());
            response.setManagerFirstName(manager.getFirstName());
            response.setManagerLastName(manager.getLastName());
        }

        Car car = request.getCar();

        response.setCarId(car.getId());
        response.setBrand(car.getBrand().getName());
        response.setModel(car.getModel());
        response.setYear(car.getYear());
        response.setMileage(car.getMileage());

        response.setComment(request.getComment());
        response.setStatus(request.getStatus());
        response.setRejectReason(request.getRejectReason());
        response.setCreatedAt(request.getCreatedAt());

        return response;
    }
}
