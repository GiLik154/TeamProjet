package com.example.team_project.domain.domain.product.category.service.registration;


import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRegistrationCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategory insertCategory(String category){

        //품목 카테고리
        ProductCategory productCategory = new ProductCategory(
                ProductCategoryStatus.valueOf(category));
                return productCategoryRepository.save(productCategory);
    }


}
