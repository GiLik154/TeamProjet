package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.InvalidPaymentMethodException;
import com.example.team_project.exception.NotFoundAddressException;
import com.example.team_project.exception.OrderListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListUpdateServiceImpl implements OrderListUpdateService {

    private final OrderListRepository orderListRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void update(Long userId, Long orderListId, Long userAddressId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(NotFoundAddressException::new);

        orderListRepository.findByUserIdAndId(userId, orderListId).ifPresent(orderList -> {
            validateOrderList(orderList.getId());
            orderList.update(userAddress);
        });
    }

    @Override
    public void update(Long userId, Long orderListId, Long userAddressId, Long paymentId) {
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(NotFoundAddressException::new);
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(InvalidPaymentMethodException::new);

        orderListRepository.findByUserIdAndId(userId, orderListId).ifPresent(orderList -> {
            validateOrderList(orderList.getId());
            orderList.update(userAddress, payment);
        });
    }

    /**
     * 오더리스트의 상태가 false 즉, 사용할 수 없는 리스트인 경우라면 익셉션 발생
     **/
    private void validateOrderList(Long orderListId) {
        OrderList orderList = orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);
        boolean orderListStatus = orderList.isStatus();

        if (!orderListStatus) {
            throw new OrderListNotFoundException();
        }
    }

}
