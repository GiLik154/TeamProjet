package com.example.team_project.domain.domain.product.likecount.service;

import com.example.team_project.domain.domain.product.likecount.domain.ProductLike;
import com.example.team_project.domain.domain.product.likecount.domain.ProductLikeRepository;
import com.example.team_project.domain.domain.product.likecount.service.dto.ProductLikeDto;
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
public class ProductRegistrationLikeService {

    private final ProductLikeRepository productLikeRepository;

    //가격,재고 등록
    public ProductLike insertLike(Product product, ProductLikeDto productLikeDto) {

        ProductLike productLike = new ProductLike(
                product,
                productLikeDto.getLikeCount());


        return productLikeRepository.save(productLike);
    }
}
