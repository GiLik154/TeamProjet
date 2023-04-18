package com.example.team_project.domain.domain.product.product.service.update;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
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
import com.example.team_project.exception.NotPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductUpdateService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductCategoryRepository productCategoryRepository;
    private final ImageUploadService imageUploadService;

    private ProductCategory getCategory(ProductCategoryStatus status) {

        return productCategoryRepository.findByStatus(status)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));
    }

    //판매자 고유번호로 수정해야함.
    //이름,이미지,가격,재고,상세설명,카테고리 수정
    public void update(Long sellerId, Long productId, String password, ProductDto productDto, MultipartFile multipartFile) {
        Seller seller = sellerRepository.validateSellerId(sellerId);

        // 판매자 비밀번호 검증
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new NotPasswordException("Invalid password");
        }

        ProductCategoryStatus productCategoryStatus = ProductCategoryStatus.valueOf(productDto.getCategoryDto());

        productRepository.findById(productId).ifPresent(product -> {
            product.update(productDto.getName(), productDto.getImage(), productDto.getDescription(),
                    productDto.getStock(), productDto.getPrice(), getCategory(productCategoryStatus));
            imageUploadService.upload(productDto.getName(), multipartFile, product);

        });
    }
}
