package com.example.team_project.domain.domain.product.product.service.delete;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.domain.domain.shop.shop.service.delete.ShopDeleteService;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.NotPasswordException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductDeleteServiceTest {

    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopDeleteService shopDeleteService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductDeleteService productDeleteService;


    @Autowired
    public ProductDeleteServiceTest(SellerRepository sellerRepository, ShopRepository shopRepository, PasswordEncoder passwordEncoder, ShopDeleteService shopDeleteService, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, ProductDeleteService productDeleteService) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.shopDeleteService = shopDeleteService;
        this.passwordEncoder = passwordEncoder;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.productDeleteService = productDeleteService;
    }

    @Test
    void 삭제_성공(){
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        //카테고리 enum 저장
        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Product product = new Product(
                "asd", shop.getSeller(), "zxczxc", 1, 10, productCategory);

        productRepository.save(product);
        productDeleteService.delete(seller.getId(),product.getId(),"testPw");

        assertFalse(productRepository.findById(product.getId()).isPresent());

    }

    @Test
    void 삭제_실패_비밀번호틀림(){
        //판매자,판매처 생성
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);

        //카테고리 enum 저장
        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);

        Product product = new Product(
                "asd", shop.getSeller(), "zxczxc", 1, 10, productCategory);

        productRepository.save(product);
        NotPasswordException e = assertThrows(NotPasswordException.class, () ->
                productDeleteService.delete(seller.getId(),product.getId(),"tesstPw")
                );



        assertTrue(productRepository.findById(product.getId()).isPresent());

    }




}