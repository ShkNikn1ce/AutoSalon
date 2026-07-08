package com.Nikita.AutoSalon.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateSaleRequest {
    private Long purchaseRequestId;
    private Long managerId;
}
