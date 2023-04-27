package com.example.team_project.controller.core.payment.dto;

import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;
import com.example.team_project.domain.domain.payment.dto.PaymentAddServiceDto;

public class PaymentControllerDto {
    /**
     * 카드/계좌명
     */
    private final String paymentName;
    /**
     * 결제 수단 종류
     */
    private final String paymentType;
    /**
     * 카드/계좌번호
     */
    private final String number;
    /**
     * 청구금액
     */


    public PaymentControllerDto(String paymentName, String paymentType, String number) {
        this.paymentName = paymentName;
        this.paymentType = paymentType;
        this.number = number;

    }

    public PaymentAddServiceDto convertServiceDto() {
        return new PaymentAddServiceDto(this.paymentName, this.paymentType, this.number);
    }
}
