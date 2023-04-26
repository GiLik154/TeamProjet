package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.order.list.service.OrderListAddService;
import com.example.team_project.domain.domain.order.list.service.OrderListAddServiceImpl;
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
public class OrderCreateServiceImpl implements OrderCreateService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderListAddService orderListAddService;
    private final UserCouponRepository userCouponRepository;
    private final OrderListAddServiceImpl orderListAddServiceImpl;

    @Override
    public Long create(Long userId, Long productId, int quantity, Long couponId) {
        User user = userRepository.validateUserId(userId);
        OrderList orderList = findAvailableOrderList(userId);

        Order order = new Order(user, orderList, createOrderToProduct(productId, quantity));
        orderRepository.save(order);

        orderToUpdateApplyCoupon(userId, order, couponId);

        return order.getId();
    }

    private OrderList findAvailableOrderList(Long userId) {
        return orderListRepository.findByUserIdAndStatus(userId, true).orElseGet(() ->
                orderListAddService.add(userId)
        );
    }

    private OrderToProduct createOrderToProduct(Long productId, int quantity) {
        Product product = productRepository.validateProductId(productId);

        OrderToProduct orderToProduct = new OrderToProduct(product, quantity);

        product.increaseSalesCountAndDecreaseStock(quantity);

        return orderToProduct;
    }

    private void orderToUpdateApplyCoupon(Long userId, Order order, Long couponId) {
        userCouponRepository.findByUserIdAndIdAndStatusUnused(userId, couponId).ifPresent(order::couponUpdate);
    }

}
