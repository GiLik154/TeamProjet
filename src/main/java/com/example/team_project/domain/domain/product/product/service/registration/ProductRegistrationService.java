package com.example.team_project.domain.domain.product.product.service.registration;

import com.example.team_project.domain.domain.image.service.ImageUploadService;
import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;

import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import com.example.team_project.exception.MinMaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegistrationService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SellerRepository sellerRepository;
    private final ImageUploadService imageUploadService;

    //product 상품 등록
    public void productRegistration(Long sellerId,ProductDto productDto, MultipartFile multipartFile) {
        ProductCategoryStatus productCategoryStatus = ProductCategoryStatus.valueOf(productDto.getCategoryDto());



        //품목이름,이미지,상세설명 등록
        Product product = new Product(
                productDto.getName(),
                getSeller(sellerId),
                productDto.getDescription(),
                productDto.getStock(),
                productDto.getPrice(),
                getCategory(productCategoryStatus));

        imageUploadService.upload(productDto.getName(), multipartFile, product);
        productRepository.save(product);
    }

    private Seller getSeller(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("shop id ."));
    }

    /**
     * DB에 값이 존재하지 않으면 만들어줌(enum은있어야함)
     */

    private ProductCategory getCategory(ProductCategoryStatus status) {
        return productCategoryRepository.findByStatus(status)
                .orElseGet(() -> {
                    ProductCategory productCategory = new ProductCategory(status);
                    productCategoryRepository.save(productCategory);
                    return productCategory;
                });
    }
}