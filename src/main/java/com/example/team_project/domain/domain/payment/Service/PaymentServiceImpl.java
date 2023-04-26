package com.example.team_project.domain.domain.payment.Service;


import com.example.team_project.controller.core.payment.add.dto.PaymentAddControllerDto;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.exception.InvalidCostException;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.PaymentNotFoundException;
import com.example.team_project.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @Override
    public int pay(Long userId, Long orderListId, Long paymentId) {
        Optional<Order> orders = orderRepository.findByOrderListId(orderListId);

        int cost = 0;

        if (orders.isPresent()) {

            cost = orders.stream().mapToInt(Order::getTotalPrice).sum();

            Payment payment = orderListRepository.findPaymentByIdAndPaymentId(orderListId, paymentId)
                    .orElseThrow(() -> new PaymentNotFoundException());

            payment.addBilling(cost);
        }

        new OrderNotFoundException();

        return cost;
    }

    @Override
    public int refund(Long userId, Long orderListId, Long paymentId) {
        Optional<Order> orders = orderRepository.findByOrderListId(orderListId);

        int cost = 0;

        if (orders.isPresent()) {
            cost = orders.stream().mapToInt(Order::getTotalPrice).sum();

            Payment payment = orderListRepository.findPaymentByIdAndPaymentId(orderListId, paymentId)
                    .orElseThrow(() -> new PaymentNotFoundException());

            payment.subtractBilling(cost);

            if (payment.getBilling() < 0) {
                throw new InvalidCostException();
            }

        }

        new OrderNotFoundException();

        return cost;
    }

    @Override
    public void registerPayment(Long userId, PaymentType paymentType, String number) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Payment payment = new Payment(user, paymentType, number);
        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(Long userId, Long paymentId, PaymentType paymentType, String number) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        payment.changePayment(user, paymentType, number);
        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long userId, Long paymentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        paymentRepository.delete(payment);

    }
}
