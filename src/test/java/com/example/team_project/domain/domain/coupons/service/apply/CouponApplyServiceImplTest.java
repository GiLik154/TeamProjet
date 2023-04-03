package com.example.team_project.domain.domain.coupons.service.apply;

import com.example.team_project.domain.domain.address.domain.UserAddress;
import com.example.team_project.domain.domain.address.domain.UserAddressRepository;
import com.example.team_project.domain.domain.coupons.domain.*;
import com.example.team_project.domain.domain.order.item.domain.Order;
import com.example.team_project.domain.domain.order.item.domain.OrderRepository;
import com.example.team_project.domain.domain.order.item.domain.OrderToProduct;
import com.example.team_project.domain.domain.order.list.domain.OrderList;
import com.example.team_project.domain.domain.order.list.domain.OrderListRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.NotApplyCouponException;
import com.example.team_project.exception.NotFoundCouponException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponApplyServiceImplTest {
    private final CouponApplyService couponApplyService;
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;
    private final UserHaveCouponRepository userHaveCouponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    public CouponApplyServiceImplTest(CouponApplyService couponApplyService, UserRepository userRepository, UserAddressRepository userAddressRepository, OrderListRepository orderListRepository, OrderRepository orderRepository, UserHaveCouponRepository userHaveCouponRepository, CouponInCategoryRepository couponInCategoryRepository, CouponKindsRepository couponKindsRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.couponApplyService = couponApplyService;
        this.userRepository = userRepository;
        this.userAddressRepository = userAddressRepository;
        this.orderListRepository = orderListRepository;
        this.orderRepository = orderRepository;
        this.userHaveCouponRepository = userHaveCouponRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Test
    void 쿠폰_적용_정상작동() {
        User user = new User("testName", "testPw");
        userRepository.save(user);

        UserAddress userAddress = new UserAddress(user, "최지혁", "받는이", "010-0000-0000", "서울특별시 강남구", "강남아파드101호", "11111");
        userAddressRepository.save(userAddress);

        OrderList orderList = new OrderList(user, userAddress, "카드");
        orderListRepository.save(orderList);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds);
        userHaveCouponRepository.save(userHaveCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        OrderToProduct orderToProduct = new OrderToProduct(product, 10);

        Order order = new Order(user, orderList, orderToProduct);
        orderRepository.save(order);

        int salePrice = couponApplyService.apply(user.getId(), userHaveCoupon.getId(), order);

        assertEquals(23750, salePrice);
    }

    @Test
    void 쿠폰_적용_정상작동_카테고리여러개() {
        User user = new User("testName", "testPw");
        userRepository.save(user);

        OrderList orderList = new OrderList(user, "testPayment");
        orderListRepository.save(orderList);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds);
        userHaveCouponRepository.save(userHaveCoupon);

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

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, c);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, c);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, product, 5);
        orderRepository.save(order);

        int salePrice = couponApplyService.apply(user.getId(), userHaveCoupon.getId(), order);

        assertEquals(23750, salePrice);
    }

    @Test
    void 쿠폰_적용_최저가격_못맞춤() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        OrderList orderList = new OrderList(user, "testPayment");
        orderListRepository.save(orderList);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 1000000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds);
        userHaveCouponRepository.save(userHaveCoupon);
        Long couponId = userHaveCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, product, 5);
        orderRepository.save(order);

        NotApplyCouponException e = assertThrows(NotApplyCouponException.class, () ->
                couponApplyService.apply(userId, couponId, order));

        assertEquals("Not Apply Coupon", e.getMessage());
    }

    @Test
    void 쿠폰_적용_유저_고유번호_다름() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        OrderList orderList = new OrderList(user, "testPayment");
        orderListRepository.save(orderList);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds);
        userHaveCouponRepository.save(userHaveCoupon);
        Long couponId = userHaveCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, product, 5);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId + 1L, couponId, order));

        assertEquals("Not Found Coupon", e.getMessage());
    }

    @Test
    void 쿠폰_적용_쿠폰_고유번호_다름() {
        User user = new User("testName", "testPw");
        userRepository.save(user);
        Long userId = user.getId();

        OrderList orderList = new OrderList(user, "testPayment");
        orderListRepository.save(orderList);

        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        UserHaveCoupon userHaveCoupon = new UserHaveCoupon(user, couponKinds);
        userHaveCouponRepository.save(userHaveCoupon);
        Long couponId = userHaveCoupon.getId();

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Order order = new Order(user, orderList, product, 5);
        orderRepository.save(order);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponApplyService.apply(userId, couponId + 1L, order));

        assertEquals("Not Found Coupon", e.getMessage());
    }
}