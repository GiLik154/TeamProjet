package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @Enumerated(EnumType.STRING)
        private PaymentType paymentType;

        private String cardNumber;

        private String accountNumber;

        private int billing;

        protected Payment() {
        }


//        public Payment(User user, PaymentType paymentType, String cardNumber, String accountNumber) {
//                this.user = user;
//                this.paymentType = paymentType;
//                this.cardNumber = cardNumber;
//                this.accountNumber = accountNumber;
//        }

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
//        public void changePayment(Long userId, PaymentType paymentType, String changedNumber) {
//                this.user =
//                this.paymentType = paymentType;
//                switch (paymentType) {
//                        case CARD:
//                                this.cardNumber = changedNumber;
//                                break;
//                        case TRANSFER:
//                                this.accountNumber = changedNumber;
//                }
//        }

        public void addBilling(int cost) {
                this.billing += cost;
        }

        public void subtractBilling(int cost) {
                this.billing -= cost;
        }

}
