package com.example.team_project.domain.domain.coupons.couponincategory.service.delete;

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
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponInCategoryDeleteServiceImplTest {
    private final CouponInCategoryDeleteService couponInCategoryDeleteService;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    @Autowired
    CouponInCategoryDeleteServiceImplTest(CouponInCategoryDeleteService couponInCategoryDeleteService, CouponInCategoryRepository couponInCategoryRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.couponInCategoryDeleteService = couponInCategoryDeleteService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Test
    void 쿠폰_카테고리_연결_삭제() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("productTest", null, "imageTest", 1, 10, null);
        productRepository.save(product);


        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponInCategoryDeleteService.deleteByCouponId(coupon);

        assertTrue(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }

    @Test
    void 쿠폰_카테고리_연결_모두_삭제() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("productTest", null, "imageTest", 1, 10, null);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);
        CouponInCategory couponInCategory2 = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory2);

        /* 2개 저장 된 것 검증 */
        assertEquals(2, couponInCategoryRepository.findByCouponName("testName").size());

        couponInCategoryDeleteService.deleteByCouponId(coupon);

        /* 모두 삭제 */
        assertTrue(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }

    @Test
    void 쿠폰_카테고리_쿠폰종류_없음() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        Coupon testCoupon = new Coupon("testName2", 10, 5, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("productTest", null, "imageTest", 1, 10, null);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponInCategoryDeleteService.deleteByCouponId(testCoupon);

        assertFalse(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }
}