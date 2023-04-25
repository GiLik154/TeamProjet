package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderCompletionServiceImplTest {

    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final PaymentRepository paymentRepository;
    private final SellerRepository sellerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderCreateService orderCreateService;
    private final OrderListRepository orderListRepository;
    private final ProductCategoryRepository productCategoryRepository;

    OrderCompletionServiceImplTest(UserRepository userRepository,
                                   UserAddressRepository userAddressRepository,
                                   UserCouponRepository userCouponRepository,
                                   CouponRepository couponRepository,
                                   PaymentRepository paymentRepository,
                                   SellerRepository sellerRepository,
                                   OrderRepository orderRepository,
                                   ProductRepository productRepository,
                                   OrderCreateService orderCreateService,
                                   OrderListRepository orderListRepository,
                                   ProductCategoryRepository productCategoryRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.paymentRepository = paymentRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderCreateService = orderCreateService;
        this.orderListRepository = orderListRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void w
}