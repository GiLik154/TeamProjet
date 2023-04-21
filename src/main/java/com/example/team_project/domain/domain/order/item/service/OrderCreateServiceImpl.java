package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListAddServiceImpl;
import com.example.team_project.domain.domain.payment.Service.PaymentService;
import com.example.team_project.domain.domain.payment.Service.PaymentServiceImpl;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateServiceImpl implements OrderCreateService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderListRepository orderListRepository;
    private final OrderListAddServiceImpl orderListAddServiceImpl;
    private final PaymentService paymentService;


    @Override
    public Order create(Long userId, Long productId, int quantity, Long userAddressId, Long paymentId) {
        User user = userRepository.validateUserId(userId);
        Product product = productRepository.validateProductId(productId);
        OrderList orderList = findAvailableOrderList(userId, userAddressId, paymentId);

        paymentService.pay(userId, orderList.getId(), paymentId);

        OrderToProduct orderToProduct = new OrderToProduct(product, quantity);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        return order;
    }


    private OrderList findAvailableOrderList(Long userId, Long userAddressId, Long paymentId) {
        return orderListRepository.findByUserIdAndStatus(userId, true).orElseGet(() ->
                orderListAddServiceImpl.add(userId, userAddressId, paymentId)
        );
    }

}
