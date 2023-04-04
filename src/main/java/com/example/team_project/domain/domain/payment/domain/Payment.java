package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제수단 고유번호

    private Long userId;

    @Enumerated(EnumType.STRING)
    // 결제수단의 종류
    private PaymentType paymentType;

    // 카드번호(계좌번호)
    private String cardNumber;
    protected Payment() {
    }

    public Payment(Long userId, PaymentType paymentType, String cardNumber) {
        this.userId = userId;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
    }

}
