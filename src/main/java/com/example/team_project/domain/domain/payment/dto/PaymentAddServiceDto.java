package com.example.team_project.domain.domain.payment.dto;

import com.example.team_project.enums.PaymentType;
import lombok.Getter;

@Getter
public class PaymentAddServiceDto {
    /** 카드/은행명 */
    private final String paymentName;
    /** 결제 수단 종류 */
    private final String paymentType;
    /** 카드/계좌번호 */
    private final String Number;
    /** 청구금액 */
    private final int billing;


    public PaymentAddServiceDto(String paymentName, String paymentType, String Number, int billing) {
        this.paymentName = paymentName;
        this.paymentType = paymentType;
        this.Number = Number;
        this.billing = billing;
    }
}
