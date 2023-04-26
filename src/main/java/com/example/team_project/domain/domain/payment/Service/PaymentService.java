package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.dto.PaymentAddServiceDto;
import com.example.team_project.domain.domain.payment.dto.PaymentUpdateServiceDto;
import com.example.team_project.enums.PaymentType;

import java.util.List;

public interface PaymentService {
    List<Payment> getPaymentList(Long userId);

    void addPayment(Long userId, PaymentAddServiceDto paymentAddServiceDto);

    void updatePayment(Long userId, Long paymentId, PaymentUpdateServiceDto paymentUpdateServiceDto);

    void deletePayment(Long userId, Long paymentId);

    int pay(Long userId, Long orderListId, Long paymentId);

    int refund(Long userId, Long orderListId, Long paymentId);
}
