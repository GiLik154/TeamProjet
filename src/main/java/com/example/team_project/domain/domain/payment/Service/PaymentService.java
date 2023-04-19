package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.enums.PaymentType;

import java.util.List;

public interface PaymentService {
    Payment registerPayment(Long userId, PaymentType paymentType, String cardNumber, String accountNumber);
    Payment updatePayment(Long userId, Long paymentId, PaymentType paymentType, String cardNumber, String accountNumber);
    void deletePayment(Long userId, Long paymentId);
    List<Payment> getPaymentsByUserId(Long userId);
}
