package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.enums.PaymentType;

import java.util.List;

public interface PaymentService {
    void registerPayment(Long userId, PaymentType paymentType, String number);

    void updatePayment(Long userId, Long paymentId, PaymentType paymentType, String number);

    void deletePayment(Long userId, Long paymentId);

    int pay(Long userId, Long orderListId, Long paymentId);

    int refund(Long userId, Long orderListId, Long paymentId);
}
