package com.example.team_project.domain.domain.coupons.coupon.service.apply;

import com.example.team_project.domain.domain.coupons.coupon.domain.Coupon;
import com.example.team_project.domain.domain.coupons.coupon.domain.CouponRepository;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.couponincategory.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponIsApplicableForPriceAndProductServiceImplTest {
    private final CouponIsApplicableForPriceAndProductService couponIsApplicableForPriceAndProductService;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    CouponIsApplicableForPriceAndProductServiceImplTest(CouponIsApplicableForPriceAndProductService couponIsApplicableForPriceAndProductService, CouponInCategoryRepository couponInCategoryRepository, CouponRepository couponRepository, ProductCategoryRepository productCategoryRepository, SellerRepository sellerRepository) {
        this.couponIsApplicableForPriceAndProductService = couponIsApplicableForPriceAndProductService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponRepository = couponRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.sellerRepository = sellerRepository;
    }

    @Test
    void 쿠폰_적용가능_확인_정상작동() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        boolean isTestResult = couponIsApplicableForPriceAndProductService.isApplicableForPriceAndProduct(coupon, productCategory, 25000);

        assertTrue(isTestResult);
    }

    @Test
    void 쿠폰_적용가능_확인_최저가격_만족_못함() {
        Coupon coupon = new Coupon("testName", 50, 100000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        boolean isTestResult = couponIsApplicableForPriceAndProductService.isApplicableForPriceAndProduct(coupon, productCategory, 25000);

        assertFalse(isTestResult);
    }

    @Test
    void 쿠폰_적용가능_확인_카테고리_만족_못함() {
        Coupon coupon = new Coupon("testName", 50, 100000, 1);
        couponRepository.save(coupon);

        ProductCategory productCategory1 = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory1);

        ProductCategory productCategory2 = new ProductCategory(ProductCategoryStatus.BOTTOM);
        productCategoryRepository.save(productCategory2);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory2);
        couponInCategoryRepository.save(couponInCategory);

        boolean isTestResult = couponIsApplicableForPriceAndProductService.isApplicableForPriceAndProduct(coupon, productCategory1, 25000);

        assertFalse(isTestResult);
    }

    @Test
    void 쿠폰_적용가능_확인_만료기간_지남() {
        Coupon coupon = new Coupon("testName", 50, 10000, 1);
        coupon.updateDeadline(LocalDate.now().plusDays(-3));
        couponRepository.save(coupon);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(coupon, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        boolean isTestResult = couponIsApplicableForPriceAndProductService.isApplicableForPriceAndProduct(coupon, productCategory, 25000);

        assertFalse(isTestResult);
    }
}