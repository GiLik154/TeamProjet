package com.example.team_project.domain.domain.product.product.service.update;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductUpdateServiceTest {

    private final PasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final ProductUpdateService productUpdateService;
    private final ShopRepository shopRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductUpdateServiceTest(PasswordEncoder passwordEncoder, SellerRepository sellerRepository, ProductRepository productRepository, ProductUpdateService productUpdateService, ShopRepository shopRepository, ProductCategoryRepository productCategoryRepository) {
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.productUpdateService = productUpdateService;
        this.shopRepository = shopRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Test
    void 수정_성공(){
        //판매자회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        //샵정보
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);
        System.out.println("셀러아이디임:"+seller.getId());


        //카테고리 enum 저장
        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory2 = new ProductCategory(ProductCategoryStatus.BOTTOM);
        productCategoryRepository.save(productCategory2);


        Product product = new Product(
                "asd", shop.getSeller(), "zxczxc", 1, 10, productCategory);

        productRepository.save(product);

        //상품이름,이미지,설명,재고,가격,카테고리선택
        ProductDto updatedProductDto = new ProductDto(
                "updatedName", "updatedImage", "updatedDescription", 10, 20, "BOTTOM");

        productUpdateService.update(seller.getId(),product.getId(),"testPw",updatedProductDto);

        //업데이트한 product 가지고오기
        Product productUpdate = productRepository.findById(product.getId()).get();

// 수정된 상품 정보가 예상한 대로 업데이트 되었는지 검증합니다.
        assertEquals("updatedName", product.getName());
        assertEquals("updatedImage", product.getImage());
        assertEquals("updatedDescription", product.getDescription());
        assertEquals(10, product.getPrice());
        assertEquals(20, product.getStock());
        assertEquals(ProductCategoryStatus.BOTTOM, product.getCategory().getStatus());
        assertEquals("updatedName", productUpdate.getName());
    }


    @Test
    void 수정_실패_비밀번호_틀림(){
        //판매자회원가입
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        //샵정보
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);
        System.out.println("셀러아이디임:"+seller.getId());


        //카테고리 enum 저장
        ProductCategory productCategory = new ProductCategory(ProductCategoryStatus.TOP);
        productCategoryRepository.save(productCategory);
        ProductCategory productCategory2 = new ProductCategory(ProductCategoryStatus.BOTTOM);
        productCategoryRepository.save(productCategory2);


        Product product = new Product(
                "asd", shop.getSeller(), "zxczxc", 1, 10, productCategory);

        productRepository.save(product);

        //상품이름,이미지,설명,재고,가격,카테고리선택
        ProductDto updatedProductDto = new ProductDto(
                "updatedName", "updatedImage", "updatedDescription", 10, 20, "BOTTOM");


        BadCredentialsException e = assertThrows(BadCredentialsException.class, () ->
                productUpdateService.update(seller.getId(),product.getId(),"testsPw",updatedProductDto));

        //업데이트한 product 가지고오기
        Product productUpdate = productRepository.findById(product.getId()).get();

// 수정된 상품 정보가 예상한 대로 업데이트 되었는지 검증합니다.
        assertNotEquals("updatedName", product.getName());
        assertNotEquals("updatedImage", product.getImage());
        assertNotEquals("updatedDescription", product.getDescription());
        assertNotEquals(10, product.getPrice());
        assertNotEquals(20, product.getStock());
        assertNotEquals(ProductCategoryStatus.BOTTOM, product.getCategory().getStatus());
        assertNotEquals("updatedName", productUpdate.getName());
    }
    

}