package com.example.team_project.domain.domain.coupons.service.delete.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.domain.Coupon;
import com.example.team_project.domain.domain.coupons.domain.CouponRepository;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponDeleteServiceImplTest {
    private final CouponKindsDeleteService couponKindsDeleteService;
    private final CouponRepository couponRepository;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    CouponDeleteServiceImplTest(CouponKindsDeleteService couponKindsDeleteService, CouponRepository couponRepository, CouponInCategoryRepository couponInCategoryRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.couponKindsDeleteService = couponKindsDeleteService;
        this.couponRepository = couponRepository;
        this.sellerRepository = sellerRepository;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    void 쿠폰_종류_삭제_정상작동() {
        Coupon coupon = new Coupon("testName", 5, 10000);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

                Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponKindsDeleteService.delete("testName");

        /* 쿠폰 종류 삭제 되는 것 검증 */
        assertTrue(couponRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 삭제 검증 */
        assertTrue(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }

    @Test
    void 쿠폰_종류_삭제_쿠폰종류_이름다름() {
        Coupon coupon = new Coupon("testName", 5, 10000);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

                Product product = new Product("testProduct", seller, "testImg", "testDes", 20, 5000, productCategory);
        productRepository.save(product);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponKindsDeleteService.delete("wrongName");

        /* 쿠폰 종류 조회 되는 것 검증 */
        assertFalse(couponRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 조회되는 것 검증 */
        assertFalse(couponInCategoryRepository.findByCouponName("testName").isEmpty());
    }
}