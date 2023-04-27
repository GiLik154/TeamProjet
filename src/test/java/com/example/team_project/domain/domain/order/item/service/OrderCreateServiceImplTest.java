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
import com.example.team_project.exception.*;
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
class OrderCreateServiceImplTest {

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


    @Autowired
    OrderCreateServiceImplTest(
            SellerRepository sellerRepository,
            UserRepository userRepository,
            UserAddressRepository userAddressRepository,
            UserCouponRepository userCouponRepository,
            CouponRepository couponRepository,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            OrderCreateService orderCreateService,
            OrderListRepository orderListRepository,
            ProductCategoryRepository productCategoryRepository) {
        this.sellerRepository = sellerRepository;
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.orderCreateService = orderCreateService;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderListRepository = orderListRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void 주문추가_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        //when
        orderCreateService.create(userId, productId, 10,  userCouponId);
        Order order = orderRepository.findByUserId(userId).orElseThrow(OrderNotFoundException::new);

        //then
        assertNotNull(order.getId());
        assertEquals(userId, order.getUser().getId());
        assertEquals(productId, order.getOrderToProduct().getProduct().getId());
        assertEquals("testProduct", order.getOrderToProduct().getProduct().getName());
        assertEquals("testDes", order.getOrderToProduct().getProduct().getDescription());
        assertEquals(userCoupon, order.getUserCoupon());
    }

    @Test
    void 주문추가_유효하지_않은_사용자_비정삭작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

         OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        //when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                orderCreateService.create(userId + 1L, productId, 10, userCouponId)
        );

        //then
        assertEquals("This user could not be found", exception.getMessage());
    }

    @Test
    void 주문추가_다수물건구매_정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product1 = new Product("testProduct1", seller, "testDes1", 99, 4000, productCategory);
        productRepository.save(product1);
        Product product2 = new Product("testProduct2", seller, "testDes2", 99, 5000, productCategory);
        productRepository.save(product2);
        Long product2Id = product2.getId();
        Product product3 = new Product("testProduct3", seller, "testDes3", 99, 3000, productCategory);
        productRepository.save(product3);

        //when
        orderCreateService.create(userId, product2Id, 10,  userCouponId);
        Order order = orderRepository.findByUserId(userId).orElseThrow(OrderNotFoundException::new);

        //then
        assertEquals("testProduct2", order.getOrderToProduct().getProduct().getName());
        assertEquals("testDes2", order.getOrderToProduct().getProduct().getDescription());
        assertEquals(50000, order.getOrderToProduct().getTotalPrice());
    }

    @Test
    void 주문추가_유효하지_않는_상품_비정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

         OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        //when
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () ->
                orderCreateService.create(userId, productId + 1L, 10, userCouponId)
        );

        //then
        assertEquals("This product does not exist", exception.getMessage());
    }

    @Test
    void 주문추가_주문개수0이하_비정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

         OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        //when
        InvalidQuantityException exception = assertThrows(InvalidQuantityException.class, () ->
                orderCreateService.create(userId, productId, 0, userCouponId)
        );

        //then
        assertEquals("Please enter the correct number", exception.getMessage());
    }

    @Test
    void 주문추가_재고소진_비정상작동() {
        //given
        User user = new User("testId", "testPassword", "testName", "testEmail", "testPhone");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);
        Long userCouponId = userCoupon.getId();

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        Payment payment = new Payment(user, "paymentName", PaymentType.CARD, "1111");
        paymentRepository.save(payment);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testDes", 0, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

         OrderList orderList = new OrderList(user, userAddress, payment, LocalDate.now());
        orderListRepository.save(orderList);

        //when
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->
                orderCreateService.create(userId, productId, 10, userCouponId)
        );

        //then
        assertEquals("This product is sold out", exception.getMessage());
    }
}