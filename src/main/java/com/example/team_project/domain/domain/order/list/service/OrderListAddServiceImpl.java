package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.exception.InvalidAddressException;
import com.example.team_project.exception.InvalidPaymentMethodException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListAddServiceImpl implements OrderListAddService {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderListRepository orderListRepository;


    @Override
    public OrderList add(Long userId, Long userAddressId, Long paymentId) {

        User user = userRepository.validateUserId(userId);
        UserAddress userAddress = userAddressRepository.findByUserIdAndId(userId, userAddressId).orElseThrow(InvalidAddressException::new);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(InvalidPaymentMethodException::new);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDateTime.now());
        orderListRepository.save(orderList);

        return orderList;
    }


}