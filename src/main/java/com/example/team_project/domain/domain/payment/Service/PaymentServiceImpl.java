package com.example.team_project.domain.domain.payment.Service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.payment.dto.PaymentAddServiceDto;
import com.example.team_project.domain.domain.payment.dto.PaymentUpdateServiceDto;
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

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;

    @Override
    public List<Payment> getPaymentList(Long userId) {

        List<Payment> payments = paymentRepository.findListByUserId(userId);

        return payments;
    }

    /**
     * userId와 PaymentAddServiceDto가 필요
     * 유저정보를 가지고와 dto를 통해
     * Payment 정보를 생성 후 save
     */
    @Override
    public void addPayment(Long userId, PaymentAddServiceDto paymentAddServiceDto) {
        Payment payment = new Payment(getUser(userId), paymentAddServiceDto.getPaymentName(),
                PaymentType.valueOf(paymentAddServiceDto.getPaymentType()),
                paymentAddServiceDto.getNumber());
        paymentRepository.save(payment);
    }

    private User getUser(Long userId) {
        return userRepository.validateUserId(userId);
    }

    @Override
    public void updatePayment(Long userId, Long paymentId, PaymentUpdateServiceDto paymentUpdateServiceDto) {
        {
            Optional<Payment> optionalPayment = paymentRepository.findByUserIdAndId(userId, paymentId);

            optionalPayment.ifPresent(payment ->
                    payment.changePayment(paymentUpdateServiceDto.getPaymentName(),
                            PaymentType.valueOf(paymentUpdateServiceDto.getPaymentType()),
                            paymentUpdateServiceDto.getNumber(),
                            paymentUpdateServiceDto.getBilling()));

        }
    }

    @Override
    public void deletePayment(Long userId, Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findByUserIdAndId(userId, paymentId);

        optionalPayment.ifPresent(paymentRepository::delete);
    }

    @Override
    public int pay(Long userId, Long orderListId, Long paymentId) {
        Optional<Order> orders = orderRepository.findByOrderListId(orderListId);

        int cost = 0;

        if (orders.isPresent()) {

            cost = orders.stream().mapToInt(Order::getTotalPrice).sum();

            Payment payment = orderListRepository.findPaymentByIdAndPaymentId(orderListId, paymentId)
                    .orElseThrow(PaymentNotFoundException::new);

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
                    .orElseThrow(PaymentNotFoundException::new);

            payment.subtractBilling(cost);

            if (payment.getBilling() < 0) {
                throw new InvalidCostException();
            }

        }

        new OrderNotFoundException();

        return cost;
    }
}
