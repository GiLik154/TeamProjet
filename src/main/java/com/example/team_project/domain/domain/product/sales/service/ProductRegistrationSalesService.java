package com.example.team_project.domain.domain.product.sales.service;


import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.sales.domain.ProductSales;
import com.example.team_project.domain.domain.product.sales.domain.ProductSalesRepository;
import com.example.team_project.domain.domain.product.sales.service.dto.ProductSalesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductRegistrationSalesService {

    private final ProductSalesRepository productSalesRepository;

    
    //판매량 등록
    public ProductSales insertSales(Product product, ProductSalesDto productSalesDto){
        ProductSales productSales = new ProductSales(
                product,
                productSalesDto.getProductSales());

                return productSalesRepository.save(productSales);
    }


}
