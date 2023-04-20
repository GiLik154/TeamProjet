package com.example.team_project.domain.domain.payment.Service;


import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.exception.PaymentNotFountException;
import com.example.team_project.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    PaymentServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository, OrderRepository orderRepository, OrderListRepository orderListRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderListRepository = orderListRepository;
    }



    @Override
    public String pay(Long userId, Long orderListId, Long paymentId) {
        List<Order> orders = orderRepository.findListByOrderListId(orderListId);

        int cost = 0;

        for (Order order : orders) {
            cost += order.getTotalPrice();
        }
        paymentRepository.getReferenceById(paymentId).addBilling(cost);

        return cost + " is Charged";
    }

    @Override
    public String refund(Long userId, Long orderListId) {
        List<Order> orders = orderRepository.findListByOrderListId(orderListId);

        int cost = 0;

        for (Order order : orders) {
            cost += order.getTotalPrice();
        }

        paymentRepository.findByOrderListId(orderListId).subtractBilling(cost);
    }

    @Override
    public void registerPayment(Long userId, PaymentType paymentType, String number) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        Payment payment = new Payment(paymentType, number);
        paymentRepository.save(payment);
    }

    @Override
    public void updatePayment(Long userId, Long paymentId, PaymentType paymentType, String number) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        payment.changePayment(paymentType, number);
        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long userId, Long paymentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new IllegalArgumentException("Invalid payment id"));

        paymentRepository.delete(payment);
    }

}
