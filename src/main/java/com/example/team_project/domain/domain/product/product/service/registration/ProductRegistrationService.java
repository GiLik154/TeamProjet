package com.example.team_project.domain.domain.product.product.service.registration;

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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegistrationService {

    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final SellerRepository sellerRepository;
    private final ImageUploadService imageUploadService;

    private Seller getSeller(Long sellerId){
        return sellerRepository.findById(sellerId).orElseThrow(() -> new RuntimeException("shop id ."));
    }

    private ProductCategory getCategory(ProductCategoryStatus status){
        return productCategoryRepository.findByStatus(status)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));
    }


    //product 상품 등록
    public void productRegistration(Long sellerId, ProductDto productDto, MultipartFile multipartFile) {
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




}
