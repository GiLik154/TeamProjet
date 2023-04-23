package com.example.team_project.domain.domain.user.service.purchase;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.OrderStatus;
import com.example.team_project.exception.OutOfStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {
    private final UserRepository userRepository;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;


//    public void purchase(Long userId, Long orderListId) {
//        Optional<OrderList> orderListOptional = orderListRepository.findByUserIdAndId(userId, orderListId);
//        if (orderListOptional.isPresent()) {
//            List<Order> orders = orderRepository.findListByOrderListId(orderListId);
//            for (Order order : orders) {
//                order.getOrderToProduct().updateStatus(OrderStatus.ORDERED);
//
//            }
//        }
//
//        orderRepository.findByUserIdAndOrderListId(userId, orderListId)
//                .forEach(order ->
//                        order.getOrderToProduct().updateStatus(OrderStatus.ORDERED));
//    }
}
