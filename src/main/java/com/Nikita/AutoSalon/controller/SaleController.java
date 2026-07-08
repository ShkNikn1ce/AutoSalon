package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.request.CreateSaleRequest;
import com.Nikita.AutoSalon.dto.response.SaleDetailResponse;
import com.Nikita.AutoSalon.dto.response.SaleResponse;
import com.Nikita.AutoSalon.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public SaleDetailResponse createSale(@RequestBody CreateSaleRequest request){
        return saleService.createSale(request);
    }

    @GetMapping("/customer/{customerId}")
    public List<SaleDetailResponse> getSalesCustomer(@PathVariable Long customerId){
        return  saleService.findSalesByCustomer(customerId);
    }

    @GetMapping("/manager/{managerId}")
    public List<SaleResponse> getSalesManager(@PathVariable Long managerId){
        return saleService.findSalesByManager(managerId);
    }
}
