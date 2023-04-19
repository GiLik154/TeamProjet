package com.example.team_project.domain.domain.payment.Service;


import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
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
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = new Payment(user, paymentType, cardNumber, accountNumber);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long userId, Long paymentId, PaymentType paymentType, String cardNumber, String accountNumber) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        if (!payment.getUser().equals(user)) {
            throw new IllegalArgumentException("Invalid payment id");
        }

        Payment updatedPayment = new Payment(payment.getId(), user, paymentType, cardNumber, accountNumber);
        return paymentRepository.save(updatedPayment);
    }

    @Override
    public void deletePayment(Long userId, Long paymentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        if (!payment.getUser().equals(user)) {
            throw new IllegalArgumentException("Invalid payment id");
        }

        paymentRepository.delete(payment);
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        return paymentRepository.findAll();
    }

}