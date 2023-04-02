package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결제수단 고유번호

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type") // 결제수단의 종류
    private PaymentType paymentType;

    @Column(name = "card_number") // 카드번호(계좌번호)
    private String cardNumber;

    public Payment(Long userId, PaymentType paymentType, String cardNumber) {
        this.userId = userId;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
    }
}
