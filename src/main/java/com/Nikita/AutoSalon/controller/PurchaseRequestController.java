package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.CreatePurchaseRequestRequest;
import com.Nikita.AutoSalon.dto.PurchaseRequestResponse;
import com.Nikita.AutoSalon.enums.PurchaseRequestStatus;
import com.Nikita.AutoSalon.service.PurchaseRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/purchaseRequests")
public class PurchaseRequestController {
    private final PurchaseRequestService purchaseRequestService;

    @PostMapping
    public PurchaseRequestResponse createPurchaseRequest(@RequestBody CreatePurchaseRequestRequest request){
        return purchaseRequestService.createRequest(request);
    }

    @GetMapping
    public List<PurchaseRequestResponse> getPurchaseRequestByStatus(@PathVariable PurchaseRequestStatus status){
        return purchaseRequestService.findAllByStatus(status);
    }

    @GetMapping("/customer/{customerId}")
    public List<PurchaseRequestResponse> getPurchaseRequestOfCustomer(@PathVariable Long customerId){
        return purchaseRequestService.findCustomerRequests(customerId);
    }

    @GetMapping("/manager/{managerId}")
    public List<PurchaseRequestResponse> getPurchaseRequestOfManager(@PathVariable Long managerId){
        return purchaseRequestService.findManagerRequests(managerId);
    }

    @PutMapping("/{purchaseRequestId}/assign/{managerId}")
    public PurchaseRequestResponse purchaseRequestAssign(@PathVariable Long purchaseRequestId, @PathVariable Long managerId){
        return purchaseRequestService.assignManager(purchaseRequestId, managerId);
    }

    @PutMapping("/{purchaseRequestId}/approve")
    public PurchaseRequestResponse approvePurchaseRequest(@PathVariable Long purchaseRequestId){
        return purchaseRequestService.approveRequest(purchaseRequestId);
    }

    @PutMapping("/{purchaseRequestId}/reject")
    public PurchaseRequestResponse rejectPurchaseRequest(@PathVariable Long purchaseRequestId, @RequestParam String reason){
        return purchaseRequestService.rejectRequest(purchaseRequestId,reason);
    }
}
