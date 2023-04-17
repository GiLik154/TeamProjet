package com.example.team_project.domain.domain.payment.domain;

import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.enums.PaymentType;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

        @OneToMany(mappedBy = "orderList")
        private List<OrderList> orderLists;

        protected Payment() {
        }

        public Payment(User user, PaymentType paymentType, String cardNumber, String accountNumber) {
                this.user = user;
                this.paymentType = paymentType;
                this.cardNumber = cardNumber;
                this.accountNumber = accountNumber;
        }

        public Payment(Long id, User user, PaymentType paymentType, String cardNumber, String accountNumber) {
                this.id = id;
                this.user = user;
                this.paymentType = paymentType;
                this.cardNumber = cardNumber;
                this.accountNumber = accountNumber;
        }

}
