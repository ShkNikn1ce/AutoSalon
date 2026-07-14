package com.Nikita.AutoSalon.mapper;

import com.Nikita.AutoSalon.dto.response.SaleDetailResponse;
import com.Nikita.AutoSalon.dto.response.SaleResponse;
import com.Nikita.AutoSalon.entity.Car;
import com.Nikita.AutoSalon.entity.PurchaseRequest;
import com.Nikita.AutoSalon.entity.Sale;
import com.Nikita.AutoSalon.entity.User;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    public SaleDetailResponse toDetailResponse(Sale request){

        SaleDetailResponse response = new SaleDetailResponse();

        response.setId(request.getId());

        Car responseCar = request.getCar();
        response.setVin(responseCar.getVin());
        response.setBrand(responseCar.getBrand().getName());
        response.setModel(responseCar.getModel());
        response.setYear(responseCar.getYear());
        response.setMileage(responseCar.getMileage());

        User responseManager = request.getManager();
        if (responseManager != null) {
            response.setManagerId(responseManager.getId());
            response.setManagerFirstName(responseManager.getFirstName());
            response.setManagerLastName(responseManager.getLastName());
        }

        User responseCustomer = request.getCustomer();
        response.setCustomerId(responseCustomer.getId());
        response.setCustomerFirstName(responseCustomer.getFirstName());
        response.setCustomerLastName(responseCustomer.getLastName());

        response.setCustomerPhone(responseCustomer.getPhone());
        response.setCustomerEmail(responseCustomer.getEmail());

        PurchaseRequest responsePurchaseRequest = request.getPurchaseRequest();
        response.setPurchaseRequestId(responsePurchaseRequest.getId());

        response.setFinalPrice(request.getFinalPrice());
        response.setSaleDate(request.getSaleDate());

        return response;
    }

    public SaleResponse toResponse(Sale request){
        SaleResponse response = new SaleResponse();

        response.setId(request.getId());
        response.setCarId(request.getId());
        response.setCustomerId(request.getCustomer().getId());
        response.setManagerId(request.getManager().getId());
        response.setFinalPrice(request.getFinalPrice());

        return response;
    }
}
