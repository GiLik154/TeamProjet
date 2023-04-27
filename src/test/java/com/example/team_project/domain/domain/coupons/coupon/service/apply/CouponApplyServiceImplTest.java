package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
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
import com.example.team_project.enums.CouponStatus;
import com.example.team_project.enums.PaymentType;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponApplyServiceImplTest {
    private final CouponApplyService couponApplyService;
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final PaymentRepository paymentRepository;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    public CouponApplyServiceImplTest(CouponApplyService couponApplyService, UserRepository userRepository, UserAddressRepository userAddressRepository, PaymentRepository paymentRepository, OrderListRepository orderListRepository, OrderRepository orderRepository, UserCouponRepository userCouponRepository, CouponInCategoryRepository couponInCategoryRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.couponApplyService = couponApplyService;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.paymentRepository = paymentRepository;
        this.orderListRepository = orderListRepository;
        this.orderRepository = orderRepository;
        this.userCouponRepository = userCouponRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Test
    void 쿠폰_적용_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 5, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateExpirationDate(LocalDate.now().plusDays(7));
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        int salePrice = couponApplyService.apply(user.getId(), userCoupon.getId(), order);

        assertEquals(47500, salePrice);
    }

    @Test
    void 쿠폰_적용_정상작동_카테고리여러개() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 5, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateExpirationDate(LocalDate.now().plusDays(+7));
        userCouponRepository.save(userCoupon);

        ProductCategory a = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(a);
        ProductCategory b = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(b);
        ProductCategory c = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(c);
        ProductCategory d = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(d);
        ProductCategory e = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(e);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 5000, c);
        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, c);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        int salePrice = couponApplyService.apply(user.getId(), userCoupon.getId(), order);

        assertEquals(47500, salePrice);
    }

    @Test
    void 쿠폰_적용_최저가격_못맞춤() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 5, 1000000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        NotApplyCouponException e = assertThrows(NotApplyCouponException.class, () ->
                couponApplyService.apply(userId, userCouponId, order));

        assertEquals("Not Apply Coupon", e.getMessage());
    }

    @Test
    void 쿠폰_적용_유저_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId + 1L, userCouponId, order));

        assertTrue(e.getMessage().contains("User does not have the coupon. Coupon ID:"));
    }

    @Test
    void 쿠폰_적용_쿠폰_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId() + 1L;

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        OrderToProduct orderToProduct = new OrderToProduct(product, 10);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId, userCouponId, order));

        assertTrue(e.getMessage().contains("User does not have the coupon. Coupon ID:"));
    }

    @Test
    void 쿠폰_적용_사용된_쿠폰() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateStatus(CouponStatus.USED);
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId, userCouponId, order));

        assertTrue(e.getMessage().contains("User does not have the coupon. Coupon ID:"));
    }

    @Test
    void 쿠폰_적용_만료된_쿠폰() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");

        paymentRepository.save(payment);

        OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateStatus(CouponStatus.EXPIRED);
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId, userCouponId, order));

        assertTrue(e.getMessage().contains("User does not have the coupon. Coupon ID:"));
    }
}