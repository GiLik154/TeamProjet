package com.example.team_project.domain.domain.coupons.service.add.couponincategory;

import com.example.team_project.domain.domain.coupons.domain.*;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.NotFoundCouponException;
import com.example.team_project.exception.NotMatchCouponCategoryException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponInCategoryAddServiceImplTest {
    private final CouponInCategoryAddService couponInCategoryAddService;
    private final CouponInCategoryRepository couponInCategoryRepository;
    private final CouponKindsRepository couponKindsRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Autowired
    CouponInCategoryAddServiceImplTest(CouponInCategoryAddService couponInCategoryAddService, CouponInCategoryRepository couponInCategoryRepository, CouponKindsRepository couponKindsRepository, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.couponInCategoryAddService = couponInCategoryAddService;
        this.couponInCategoryRepository = couponInCategoryRepository;
        this.couponKindsRepository = couponKindsRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Test
    void 쿠폰_카테고리_정상연결() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        couponInCategoryAddService.add(couponKinds.getName(), productCategory.getId());

        CouponInCategory couponInCategory = couponInCategoryRepository.findByCouponKindsName(couponKinds.getName()).get(0);

        assertNotNull(couponInCategory.getId());
        assertEquals(couponKinds.getName(), couponInCategory.getCouponKinds().getName());
        assertEquals(productCategory, couponInCategory.getProductCategory());
    }

    @Test
    void 쿠폰_카테고리_쿠폰종류_없음() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);
        Long productCategoryId = productCategory.getId();

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        NotFoundCouponException e = assertThrows(NotFoundCouponException.class, () ->
                couponInCategoryAddService.add("wrongName",
                        productCategoryId));

        assertEquals("Not Found Coupon" , e.getMessage());

    }

    @Test
    void 쿠폰_카테고리_카테고리_없음() {
        CouponKinds couponKinds = new CouponKinds("testName", 5, 10000);
        couponKindsRepository.save(couponKinds);

        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);
        Long productCategoryId = productCategory.getId();

        Seller seller = new Seller("testSellerName", "testSellerPw");
        sellerRepository.save(seller);

        Product product = new Product("testProduct", seller, "testImg", "testDes", 5, 5000, productCategory);
        productRepository.save(product);

        couponInCategoryAddService.add(couponKinds.getName(), productCategory.getId());

        NotMatchCouponCategoryException e = assertThrows(NotMatchCouponCategoryException.class, () ->
                couponInCategoryAddService.add("testName",
                        productCategoryId + 1L));

        assertEquals("Not Match Coupon Category" , e.getMessage());
    }
}