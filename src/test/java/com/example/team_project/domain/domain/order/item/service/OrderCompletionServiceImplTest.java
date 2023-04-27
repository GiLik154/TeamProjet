package com.example.team_project.domain.domain.order.item.service;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.payment.domain.Payment;
import com.example.team_project.domain.domain.payment.domain.PaymentRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.Role;
import com.example.team_project.enums.UserGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderRepository orderRepository;
    private final OrderListRepository orderListRepository;
    private final OrderCompletionService orderCompletionService;

    @Autowired
    OrderCompletionServiceImplTest(UserRepository userRepository,
                                   UserAddressRepository userAddressRepository,
                                   UserCouponRepository userCouponRepository,
                                   CouponRepository couponRepository,
                                   PaymentRepository paymentRepository,
                                   SellerRepository sellerRepository,
                                   ProductRepository productRepository,
                                   ProductCategoryRepository productCategoryRepository,
                                   OrderRepository orderRepository,
                                   OrderCompletionService orderCompletionService,
                                   OrderListRepository orderListRepository) {
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.paymentRepository = paymentRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.orderRepository = orderRepository;
        this.orderCompletionService = orderCompletionService;
        this.orderListRepository = orderListRepository;
    }

    @Test
    void 주문결제_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);
        Long userAddressId = userAddress.getId();

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);
        Long paymentId = payment.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct1", seller, "testDes1", 99, 4000, productCategory);
        productRepository.save(product);

        OrderList orderList = new OrderList(user, LocalDate.now());
        orderListRepository.save(orderList);
        Long orderListId = orderList.getId();

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        //when
        orderCompletionService.processOrderPayment(userId, userAddressId, paymentId, orderListId);

        //then
        assertFalse(orderList.isPaymentsStatus());
        assertEquals("testProduct1", order.getOrderToProduct().getProduct().getName());
        assertEquals("testDes1", order.getOrderToProduct().getProduct().getDescription());
        assertEquals(40000, order.getOrderToProduct().getTotalPrice());
        assertEquals(89, order.getOrderToProduct().getProduct().getStock());
        assertEquals(10, product.getSalesCount());
    }
}