package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.*;
import com.example.team_project.exception.OrderNotFoundException;
import com.example.team_project.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;


    public void add(Long userId, Long orderListId, Long productId, int quantity) {
        User user = userRepository.validateUserId(userId);
        OrderList orderList = orderListRepository.validateOrderListId(orderListId);
        Product product = productRepository.validateProductId(productId);

        Order order = new Order(user, orderList, product, quantity);
        orderRepository.save(order);
    }

    public void update(Long productId, Long orderId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        Order order = orderRepository.validateOrderId(orderId);
        order.update(product, quantity);
    }

    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException();
        }
        orderRepository.deleteById(orderId);
    }




}
