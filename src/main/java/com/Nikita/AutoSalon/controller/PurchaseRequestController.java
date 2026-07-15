package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.request.CreatePurchaseRequestRequest;
import com.Nikita.AutoSalon.dto.response.PurchaseRequestResponse;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.service.PurchaseRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name="Заявки", description = "API для управления заявками")
@RequestMapping("/purchaseRequests")
public class PurchaseRequestController {
    private final PurchaseRequestService purchaseRequestService;

    @Operation(
            summary = "Создать новую заявку",
            description = "Создает запись о новой заявке в системе"
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Заявка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Такая заявка уже существует")
    })
    @PostMapping
    public PurchaseRequestResponse createPurchaseRequest(@RequestBody CreatePurchaseRequestRequest request){
        return purchaseRequestService.createRequest(request);
    }

    @Operation(
            summary = "Вывести список всех заявок по статусу рассмотрения",
            description = "Выводит список всех заявок в соответствии с указанным статусом рассмотрения"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех заявок с указанным статусом рассмотрения"),
            @ApiResponse(responseCode = "404", description = "Указанный путь не найден")
    }
    )
    @GetMapping("/{status}")
    public List<PurchaseRequestResponse> getPurchaseRequestByStatus(@PathVariable PurchaseRequestStatus status){
        return purchaseRequestService.findAllByStatus(status);
    }

    @Operation(
            summary = "Вывести список всех заявок конкретного покупателя",
            description = "Выводит список всех заявок конкретного покупателя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех заявок"),
            @ApiResponse(responseCode = "404", description = "Покупатель с указанным ID не найден")
    })
    @GetMapping("/customer/{customerId}")
    public List<PurchaseRequestResponse> getPurchaseRequestOfCustomer(@PathVariable Long customerId){
        return purchaseRequestService.findCustomerRequests(customerId);
    }

    @Operation(
            summary = "Вывести список всех заявок конкретного менеджера",
            description = "Выводит список всех заявок конкретного менеджера"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех заявок"),
            @ApiResponse(responseCode = "404", description = "Менеджер с указанным ID не найден")
    })
    @GetMapping("/manager/{managerId}")
    public List<PurchaseRequestResponse> getPurchaseRequestOfManager(@PathVariable Long managerId){
        return purchaseRequestService.findManagerRequests(managerId);
    }

    @Operation(
            summary = "Назначить менеджера на новую заявку",
            description = "Назначает менеджера на заявку по его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Заявка с таким ID не найдена"),
            @ApiResponse(responseCode = "500", description = "Менеджер с таким ID не найден")
    })
    @PutMapping("/{purchaseRequestId}/assign/{managerId}")
    public PurchaseRequestResponse purchaseRequestAssign(@PathVariable Long purchaseRequestId, @PathVariable Long managerId){
        return purchaseRequestService.assignManager(purchaseRequestId, managerId);
    }

    @Operation(
            summary = "Одобрить взятую в работу заявку",
            description = "Одобряет заявку взятую в работу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка одобрена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Заявка с таким ID не найдена"),
    })
    @PutMapping("/{purchaseRequestId}/approve")
    public PurchaseRequestResponse approvePurchaseRequest(@PathVariable Long purchaseRequestId){
        return purchaseRequestService.approveRequest(purchaseRequestId);
    }

    @Operation(
            summary = "Отклонить взятую в работу заявку",
            description = "Отклоняет заявку взятую в работу"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка отклонена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Заявка с таким ID не найдена"),
    })
    @PutMapping("/{purchaseRequestId}/reject")
    public PurchaseRequestResponse rejectPurchaseRequest(@PathVariable Long purchaseRequestId, @RequestParam String reason){
        return purchaseRequestService.rejectRequest(purchaseRequestId,reason);
    }
}
