package com.Nikita.AutoSalon.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePurchaseRequestRequest {
    private Long customerId;
    private Long carId;
    private String comment;
}
