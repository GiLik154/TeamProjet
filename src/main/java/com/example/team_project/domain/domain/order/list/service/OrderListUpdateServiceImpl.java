package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.CannotCancelOrderException;
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

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void update(Long userId, Long orderListId, Long userAddressId) {

        validateUserAndOrderList(userId, orderListId);
        orderListRepository.findByUserIdAndId(userId, orderListId).ifPresent(orderList ->
                orderList.update(validateUserAddress(userAddressId)));
    }

    @Override
    public void update(Long userId, Long orderListId, Long userAddressId, Long paymentId) {

        validateUserAndOrderList(userId, orderListId);
        orderListRepository.findByUserIdAndId(userId, orderListId).ifPresent(orderList ->
                orderList.update(validateUserAddress(userAddressId), validatePayment(paymentId)));
    }

    public void existStatusShippedAndDeliveredOrder(Long userId) {
        OrderList orderList = orderListRepository.findByUserIdAndStatus(userId, true).orElseThrow(OrderListNotFoundException::new);
        List<Order> orders = orderRepository.findShippedAndDeliveredOrders(orderList.getId());
        if (!orders.isEmpty()) {
            orderList.updateStatus();
        }
    }

    /**
     * 해당 유저와 주문리스트 존재유무를 검증, 없을시 익셉션 발생
     * 또한 위 정보로 불러온 주문리스트상태가 false일 경우 사용할 수 없음(찾을수 없음)의 익셉션 발생
     **/
    private void validateUserAndOrderList(Long userId, Long orderListId) {
        userRepository.verifyUserId(userId);
        OrderList orderList = orderListRepository.findByUserIdAndId(userId, orderListId).orElseThrow(OrderListNotFoundException::new);
        boolean orderListStatus = orderList.isStatus();

        if (!orderListStatus) {
            throw new OrderListNotFoundException();
        }
    }

    /**
     * 주소 고유 Id로 존재하는지 검증, 없으면 익셉션 발생
     **/
    private UserAddress validateUserAddress(Long userAddressId) {

        return userAddressRepository.findById(userAddressId).orElseThrow(NotFoundAddressException::new);
    }

    /**
     * 결제 고유 Id로 존재하는지 검증, 없으면 익셉션 발생
     **/
    private Payment validatePayment(Long paymentId) {

        return paymentRepository.findById(paymentId).orElseThrow(InvalidPaymentMethodException::new);
    }

}
