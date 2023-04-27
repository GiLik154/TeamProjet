package com.example.team_project.domain.domain.coupons.usercoupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCoupon;
import com.example.team_project.domain.domain.coupons.usercoupon.domain.UserCouponRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.CouponStatus;
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ApplyCouponListGetServiceImplTest {
    private final ApplyCouponListGetService applyCouponListGetService;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final UserCouponRepository userCouponRepository;

    @Autowired
    ApplyCouponListGetServiceImplTest(ApplyCouponListGetService applyCouponListGetService, CouponInCategoryRepository couponInCategoryRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, UserRepository userRepository, SellerRepository sellerRepository, ProductRepository productRepository, UserCouponRepository userCouponRepository) {
        this.applyCouponListGetService = applyCouponListGetService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.userCouponRepository = userCouponRepository;
    }

    @Test
    void 적용가능_쿠폰_출력_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 25000);

        assertFalse(testCoupons.isEmpty());
    }

    @Test
    void 적용가능_쿠폰_출력_최저가격_미달() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 1000);

        assertTrue(testCoupons.isEmpty());
    }

    @Test
    void 적용가능_쿠폰_출력_카테고리_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory1 = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory1);

        ProductCategory productCategory2 = new ProductCategory(ProductCategoryStatus.SHOES);
        productCategoryRepository.save(productCategory2);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory1);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory2);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 25000);

        assertTrue(testCoupons.isEmpty());
    }

    @Test
    void 적용가능_쿠폰_출력_사용된_쿠폰() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateStatus(CouponStatus.USED);
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 1000);

        assertTrue(testCoupons.isEmpty());
    }

    @Test
    void 적용가능_쿠폰_출력_만료된_쿠폰() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCoupon.updateStatus(CouponStatus.EXPIRED);
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 1000);

        assertTrue(testCoupons.isEmpty());
    }

    @Test
    void 적용가능_쿠폰_쿠폰_자체_만료() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-3));
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDate.now());
        userCouponRepository.save(userCoupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);
        Long productId = product.getId();

        List<UserCoupon> testCoupons = applyCouponListGetService.getList(userId, productId, 1000);

        assertTrue(testCoupons.isEmpty());
    }
}