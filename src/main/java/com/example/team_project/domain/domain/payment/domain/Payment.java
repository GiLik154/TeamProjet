package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.PaymentType;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        @Enumerated(EnumType.STRING)
        private PaymentType paymentType;

        private String cardNumber;

        private String accountNumber;

        private int billing;

        protected Payment() {
        }


        public Payment(User user, PaymentType paymentType, String number) {
                this.user = user;
                this.paymentType = paymentType;
                switch (paymentType) {
                        case CARD:
                                this.cardNumber = number;
                                break;
                        case TRANSFER:
                                this.accountNumber = number;
                }

                this.billing = 0;
        }
        public void changePayment(User user, PaymentType paymentType, String changedNumber) {
                this.user = user;
                this.paymentType = paymentType;
                switch (paymentType) {
                        case CARD:
                                this.cardNumber = changedNumber;
                                break;
                        case TRANSFER:
                                this.accountNumber = changedNumber;
                }
        }

        public void addBilling(int cost) {
                this.billing += cost;
        }

        public void subtractBilling(int cost) {
                this.billing -= cost;
        }

}
