package com.example.team_project.domain.domain.coupons.coupon.service.delete;

import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.user.domain.User;
import com.example.team_project.domain.domain.user.domain.UserRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.enums.UserGrade;
import com.example.team_project.exception.UserNotCouponLevelException;
import com.example.team_project.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponDeleteServiceImplTest {
    private final CouponDeleteService couponDeleteService;
    private final CouponRepository couponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    CouponDeleteServiceImplTest(CouponDeleteService couponDeleteService, CouponRepository couponRepository, CouponInCategoryRepository couponInCategoryRepository, UserRepository userRepository, SellerRepository sellerRepository, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.couponDeleteService = couponDeleteService;
        this.couponRepository = couponRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }


    @Test
    void 쿠폰_종류_삭제_정상작동() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponDeleteService.delete(userId, "testName");

        /* 쿠폰 종류 삭제 되는 것 검증 */
        assertTrue(couponRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 삭제 검증 */
        assertTrue(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }

    @Test
    void 쿠폰_종류_삭제_쿠폰종류_이름다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponDeleteService.delete(userId, "wrongName");

        /* 쿠폰 종류 조회 되는 것 검증 */
        assertFalse(couponRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 조회되는 것 검증 */
        assertFalse(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }

    @Test
    void 쿠폰_종류_삭제_유저_고유번호_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.VIP);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () ->
                couponDeleteService.delete(userId + 1L, "testName"));


        assertEquals("This user could not be found", e.getMessage());
    }

    @Test
    void 쿠폰_종류_삭제_유저_등급_다름() {
User user = new User("testId", "testPw", "testNane", "testPhone", "testNumber");
        user.updateUserGrade(UserGrade.SILVER);
        userRepository.save(user);
        Long userId = user.getId();

        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", 99, 20, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        UserNotCouponLevelException e = assertThrows(UserNotCouponLevelException.class, () ->
                couponDeleteService.delete(userId, "testName"));

        assertTrue(e.getMessage().contains("The user tried to force a coupon user ID"));
    }
}