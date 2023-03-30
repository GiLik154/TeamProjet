package com.example.team_project.domain.domain.coupons.service.delete.couponkinds;

import com.example.team_project.domain.domain.coupons.domain.CouponInCategory;
import com.example.team_project.domain.domain.coupons.domain.CouponInCategoryRepository;
import com.example.team_project.domain.domain.coupons.domain.CouponKinds;
import com.example.team_project.domain.domain.coupons.domain.CouponKindsRepository;
import com.example.team_project.domain.domain.coupons.service.delete.couponincategory.CouponInCategoryDeleteService;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
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
class CouponKindsDeleteServiceImplTest {
    private final CouponKindsDeleteService couponKindsDeleteService;
    private final CouponKindsRepository couponKindsRepository;
    private final CouponInCategoryDeleteService couponInCategoryDeleteService;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    CouponKindsDeleteServiceImplTest(CouponKindsDeleteService couponKindsDeleteService, CouponKindsRepository couponKindsRepository, CouponInCategoryDeleteService couponInCategoryDeleteService, CouponInCategoryRepository couponInCategoryRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.couponKindsDeleteService = couponKindsDeleteService;
        this.couponKindsRepository = couponKindsRepository;
        this.couponInCategoryDeleteService = couponInCategoryDeleteService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    @Test
    void 쿠폰_종류_삭제_정상작동() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Product product = new Product();
        productRepository.save(product);

        ProductCategory productCategory = new ProductCategory(product, "testCategory");
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponKindsDeleteService.delete("testName");

        /* 쿠폰 종류 삭제 되는 것 검증 */
        assertTrue(couponKindsRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 삭제 검증 */
        assertTrue(couponInCategoryRepository.findByCouponKindsName("testName").isEmpty());
    }

    @Test
    void 쿠폰_종류_삭제_쿠폰종류_이름다름() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        Product product = new Product();
        productRepository.save(product);

        ProductCategory productCategory = new ProductCategory(product, "testCategory");
        productCategoryRepository.save(productCategory);

        CouponInCategory couponInCategory = new CouponInCategory(couponKinds, productCategory);
        couponInCategoryRepository.save(couponInCategory);

        couponKindsDeleteService.delete("wrongName");

        /* 쿠폰 종류 조회 되는 것 검증 */
        assertFalse(couponKindsRepository.findByName("testName").isEmpty());
        /* 쿠폰 종류와 카테고리 맵핑 DB 조회되는 것 검증 */
        assertFalse(couponInCategoryRepository.findByCouponKindsName("testName").isEmpty());
    }
}