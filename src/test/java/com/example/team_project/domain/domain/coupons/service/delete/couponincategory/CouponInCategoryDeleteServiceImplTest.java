package com.example.team_project.domain.domain.coupons.service.delete.couponincategory;

import com.example.team_project.domain.domain.coupons.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
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
    private final CouponKindsRepository couponKindsRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    CouponInCategoryDeleteServiceImplTest(CouponInCategoryDeleteService couponInCategoryDeleteService, CouponInCategoryRepository couponInCategoryRepository, CouponKindsRepository couponKindsRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.couponInCategoryDeleteService = couponInCategoryDeleteService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    void 쿠폰_카테고리_연결_삭제() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Product product = new Product();
        productRepository.save(product);

        ProductCategory productCategory = new ProductCategory("testCategory");
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponInCategoryDeleteService.deleteByCouponKindsId(couponKinds);

        assertTrue(couponInCategoryRepository.findByCouponKindsName("testName").isEmpty());
    }

    @Test
    void 쿠폰_카테고리_연결_모두_삭제() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Product product = new Product();
        productRepository.save(product);

        ProductCategory productCategory = new ProductCategory("testCategory");
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);
        CouponInCategory couponInCategory2 = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory2);

        /* 2개 저장 된 것 검증 */
        assertEquals(2, couponInCategoryRepository.findByCouponKindsName("testName").size());

        couponInCategoryDeleteService.deleteByCouponKindsId(couponKinds);

        /* 모두 삭제 */
        assertTrue(couponInCategoryRepository.findByCouponKindsName("testName").isEmpty());
    }

    @Test
    void 쿠폰_카테고리_쿠폰종류_없음() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        CouponKinds testCouponKinds = new CouponKinds("testName2", 10, 5);
        couponKindsRepository.save(couponKinds);

        Product product = new Product();
        productRepository.save(product);

        ProductCategory productCategory = new ProductCategory("testCategory");
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponInCategoryDeleteService.deleteByCouponKindsId(testCouponKinds);

        assertFalse(couponInCategoryRepository.findByCouponKindsName("testName").isEmpty());
    }
}