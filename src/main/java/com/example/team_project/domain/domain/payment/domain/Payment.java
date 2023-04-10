package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "payments")
        public class Payment{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // 결제수단 고유번호

        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        @Enumerated(EnumType.STRING)
        // 결제수단의 종류
        private PaymentType paymentType;

        // 카드번호(계좌번호)
        private String cardNumber;

        protected Payment(){
        }

        public Payment(User user, PaymentType paymentType, String cardNumber) {
        this.user = user;
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
    }

}
