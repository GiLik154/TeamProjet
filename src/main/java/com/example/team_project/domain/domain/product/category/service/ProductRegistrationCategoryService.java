package com.example.team_project.domain.domain.product.category.service;


import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.category.service.dto.ProductCategoryDto;
import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegistrationCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategory insertCategory(Product product, ProductCategoryDto productCategoryDto){

        //품목 카테고리
        ProductCategory productCategory = new ProductCategory(
                productCategoryDto.getProductCategory());


                return productCategoryRepository.save(productCategory);
    }


}
