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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductUpdateService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductCategoryRepository productCategoryRepository;


    private ProductCategory getCategory(ProductCategoryStatus status){

        return productCategoryRepository.findByStatus(status)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));
    }

    //판매자 고유번호로 수정해야함.

    //이름,이미지,가격,재고,상세설명,카테고리 수정
    public void update(Long sellerId, Long productId,String password, ProductDto productDto) {
        Seller seller = sellerRepository.validateSellerId(sellerId);
        // 판매자 비밀번호 검증
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }
        ProductCategoryStatus productCategoryStatus = ProductCategoryStatus.valueOf(productDto.getCategoryDto());


        productRepository.findById(productId).ifPresent(product -> {
            product.update(productDto.getName(), productDto.getImage(), productDto.getDescription(),
                    productDto.getStock(), productDto.getPrice(), getCategory(productCategoryStatus));
        });


    }


}
