package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.enums.OrderStatus;
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

    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;


    @Override
    public void update(Long userId, Long orderListId, UserAddress userAddress) {

        Optional<OrderList> orderList = orderListRepository.findByUserIdAndId(userId, orderListId);
        if (orderList.isEmpty()) {
            throw new OrderListNotFoundException();
        }
        orderList.get().update(userAddress);
    }

    /**
     * 같은 orderListId를 가진 주문들을 찾아 List 로 받고 paymentStatus 에서 stream 으로 변환 후
     * allmatch 로 order 상태가 PAYMENT_COMPLETED 와 같은지 비교한 뒤 리스트 안의 값이 맞을시
     * true 를 반환한다. 후에 if문 에서 paymentStatus 가 true(리스트 안의 모든 주문이 결제 완료인 상태)라면
     * orderList 의 상태를 update 하여 false 로 바꾸어 해당 orderList 의 status 를 false 변환
     **/
    public void paymentResult(Long orderListId) {
        List<Order> orders = orderRepository.findPaymentCompletedOrders(orderListId);
        OrderList orderList = findByOrderListById(orderListId);

        boolean paymentStatus = orders.stream().allMatch(order ->
                order.getOrderToProduct().getStatus() == OrderStatus.PAYMENT_COMPLETED);

        if (paymentStatus) {
            orderList.updateStatus();
        }
    }


    private OrderList findByOrderListById(Long orderListId) {
        return orderListRepository.findById(orderListId).orElseThrow(OrderListNotFoundException::new);
    }


}
