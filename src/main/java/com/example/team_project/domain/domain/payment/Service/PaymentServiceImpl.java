package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderListRepository orderListRepository;

    PaymentServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository, OrderListRepository orderListRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderListRepository = orderListRepository;
    }

    @Override
    public Payment registerPayment(Long userId, PaymentType paymentType, String cardNumber, String accountNumber) {
        return null;
    }

    @Override
    public Payment updatePayment(Long userId, Long paymentId, PaymentType paymentType, String cardNumber, String accountNumber) {
        return null;
    }

    @Override
    public void deletePayment(Long userId, Long paymentId) {

    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return null;
    }

    @Override
    public Payment addPaymentToOrder(Long orderListId, Long paymentId) {
        return null;
    }
}
