package com.Nikita.AutoSalon.dto.response;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class SaleResponse {
    private Long id;
    private Long carId;
    private Long customerId;
    private Long managerId;
    private BigDecimal finalPrice;
}
