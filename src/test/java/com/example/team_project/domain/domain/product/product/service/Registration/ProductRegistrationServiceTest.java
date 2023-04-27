
package com.example.team_project.domain.domain.product.product.service.Registration;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.product.product.service.registration.ProductRegistrationService;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.domain.domain.shop.shop.domain.Shop;
import com.example.team_project.domain.domain.shop.shop.domain.ShopRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRegistrationServiceTest {

    private final ProductRepository productRepository;
    private final ProductRegistrationService productRegistrationService;
    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRegistrationServiceTest(ProductRepository productRepository, ProductRegistrationService productRegistrationService, SellerRepository sellerRepository, ShopRepository shopRepository, PasswordEncoder passwordEncoder, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productRegistrationService = productRegistrationService;
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.passwordEncoder = passwordEncoder;
        this.productCategoryRepository = productCategoryRepository;
    }


    @Test
    void 상품_등록_완료() {
        //판매자생성
        byte[] imageBytes = "test-image".getBytes();
        String imageName = "test-image.jpg";
        MockMultipartFile file = new MockMultipartFile("file", imageName, "image/jpeg", imageBytes);
        Seller seller = new Seller("testId", passwordEncoder.encode("testPw"), "testName", "testPhone");
        sellerRepository.save(seller);
        //shop 생성
        Shop shop = new Shop("testshopname", "testshowaddress", "testshopbnum");
        shopRepository.save(shop);
        
        //상품이름,이미지,설명,재고,가격,카테고리선택
        ProductDto productDto = new ProductDto(
                "testName", "testimage", "testDescription", 0, 1, "TOP");
        Long sellerId = seller.getId();
        
        //서비스실행
        productRegistrationService.productRegistration(sellerId, productDto,file);

        
        //이름으로 검색하여 관련된 내용 가지고오기
        Product product = productRepository.findByName(productDto.getName()).get();
        
        //확인
        assertEquals("testName", product.getName());
        assertNotNull(product.getImage());
        assertEquals("testDescription", product.getDescription());
        assertEquals(0, product.getStock());
        assertEquals(1, product.getPrice());
        assertEquals(ProductCategoryStatus.TOP, product.getCategory().getStatus());

    }

}
