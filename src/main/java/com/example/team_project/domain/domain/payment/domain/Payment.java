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

        private String paymentName;

        @Enumerated(EnumType.STRING)
        private PaymentType paymentType;

        private String number;

        private int billing;

        protected Payment() {
        }


        public Payment(User user, String paymentName, PaymentType paymentType, String number) {
                this.user = user;
                this.paymentName = paymentName;
                this.paymentType = paymentType;
                this.number = number;

                this.billing = 0;
        }
        public void changePayment(String paymentName, PaymentType paymentType, String number, int billing) {
                this.paymentName = paymentName;
                this.paymentType = paymentType;
                this.number = number;
                this.billing = billing;
        }

        public void addBilling(int cost) {
                this.billing += cost;
        }

        public void subtractBilling(int cost) {
                this.billing -= cost;
        }

}
