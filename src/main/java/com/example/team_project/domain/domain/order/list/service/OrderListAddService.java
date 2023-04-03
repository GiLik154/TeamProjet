package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListAddService {

    private final UserRepository userRepository;
    private final OrderListRepository orderListRepository;


    /**
     * 주문 리스트 생성
     **/
    public OrderList add(Long userId, UserAddress userAddress, String paymentMethod) {

        User user = userRepository.validateUserId(userId);

        OrderList orderList = new OrderList(user, userAddress, paymentMethod);
        orderListRepository.save(orderList);

        return orderList;
    }


}