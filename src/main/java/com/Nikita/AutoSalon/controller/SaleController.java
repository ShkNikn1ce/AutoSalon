package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.request.CreateSaleRequest;
import com.Nikita.AutoSalon.dto.response.SaleDetailResponse;
import com.Nikita.AutoSalon.dto.response.SaleResponse;
import com.Nikita.AutoSalon.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name="Продажи", description = "API для управления продажами")
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    @Operation(
            summary = "Создать новую продажу",
            description = "Создает запись о новой продаже в системе"
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Продажа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Такая продажа уже существует")
    })
    @PostMapping
    public SaleDetailResponse createSale(@RequestBody CreateSaleRequest request){
        return saleService.createSale(request);
    }

    @Operation(
            summary = "Вывести список всех покупок совершенных конкретным покупателем",
            description = "Выводит список всех покупок совершенных конкретным покупателем"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех совершенных покупок"),
            @ApiResponse(responseCode = "404", description = "Покупатель с указанным ID не найден")
    })
    @GetMapping("/customer/{customerId}")
    public List<SaleDetailResponse> getSalesCustomer(@PathVariable Long customerId){
        return  saleService.findSalesByCustomer(customerId);
    }

    @Operation(
            summary = "Вывести список всех продаж проведенных конкретным менеджером",
            description = "Выводит список всех продаж проведенных конкретным менеджером"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех совершенных покупок"),
            @ApiResponse(responseCode = "404", description = "Покупатель с указанным ID не найден")
    })
    @GetMapping("/manager/{managerId}")
    public List<SaleResponse> getSalesManager(@PathVariable Long managerId){
        return saleService.findSalesByManager(managerId);
    }
}
