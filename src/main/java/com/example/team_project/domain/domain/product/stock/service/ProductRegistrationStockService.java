package com.example.team_project.domain.domain.product.stock.service;

import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.stock.domain.ProductStock;
import com.example.team_project.domain.domain.product.stock.domain.ProductStockRepository;
import com.example.team_project.domain.domain.product.stock.service.dto.ProductStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductRegistrationStockService {

    private final ProductStockRepository productStockRepository;

    
    //가격,재고 등록
    public ProductStock insertStock(Product product, ProductStockDto productStockDto) {

        ProductStock productStock = new ProductStock(
                product,
                productStockDto.getProductStock(),
                productStockDto.getProductStock());


        return productStockRepository.save(productStock);
    }
}
