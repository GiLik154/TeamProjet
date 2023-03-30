package com.example.team_project.domain.domain.product.product.service;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;

import com.example.team_project.domain.domain.product.stock.service.ProductRegistrationStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegistrationService {

    private final ProductRepository productRepository;
    private final ProductRegistrationStockService productRegistrationStockService;

    //product 상품 등록
    public void productRegistration(ProductDto productDto) {

        //품목이름,이미지,상세설명 등록
        Product product = new Product(
                productDto.getProductName(),
                productDto.getProductImage(),
                productDto.getProductDescription()
        );

        productRegistrationStockService.insertStock(product,productDto.getStockDto());
        productRepository.save(product);
    }
}
