package com.example.team_project.domain.domain.product.product.service.delete;

import com.example.team_project.domain.domain.product.category.domain.ProductCategory;
import com.example.team_project.domain.domain.product.category.domain.ProductCategoryRepository;
import com.example.team_project.domain.domain.product.product.domain.Product;
import com.example.team_project.domain.domain.product.product.domain.ProductRepository;
import com.example.team_project.domain.domain.product.product.service.dto.ProductDto;
import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import com.example.team_project.domain.domain.shop.seller.domain.SellerRepository;
import com.example.team_project.enums.ProductCategoryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductDeleteService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;



    //삭제
    public void delete(Long sellerId, Long productId,String password) {
        Seller seller = sellerRepository.validateSellerId(sellerId);

        // 판매자 비밀번호 검증
        if (!seller.isValidPassword(passwordEncoder, password)) {
            throw new BadCredentialsException("Invalid password");
        }



        productRepository.deleteById(productId);



    }



}
