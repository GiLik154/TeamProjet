package com.example.team_project.controller.core.order.create;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class OrderCreateDto {

    @NotNull
    private final Long productId;
    @Min(value = 0)
    @Max(value = 100)
    private final int quantity;
//    @NotNull
//    private final Long userAddressId;
//    @NotNull
//    private final Long paymentId;

    public OrderCreateDto(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

//    public OrderCreateDto(Long productId, int quantity, Long userAddressId, Long paymentId) {
//        this.productId = productId;
//        this.quantity = quantity;
//        this.userAddressId = userAddressId;
//        this.paymentId = paymentId;
//    }
}
