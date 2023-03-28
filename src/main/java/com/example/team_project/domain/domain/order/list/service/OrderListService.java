package com.example.team_project.domain.domain.order.list.service;

import com.example.team_project.domain.domain.order.list.Address;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderListService {

    private final OrderListRepository orderListRepository;
    private final UserRepository userRepository;


    public void add(Long userId, Address address, String paymentMethod){

        User user = userRepository.validateUserId(userId);

        OrderList orderList = new OrderList(user, address, paymentMethod);
        orderListRepository.save(orderList);
    }

    public void cancel(Long userId, Long orderListId){
        User user = userRepository.validateUserId(userId);
        OrderList orderList = orderListRepository.validateOrderListId(orderListId);

        orderList.cancel(user);
    }



}
