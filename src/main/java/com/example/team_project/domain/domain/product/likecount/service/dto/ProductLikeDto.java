package com.example.team_project.domain.domain.product.likecount.service.dto;

import com.example.team_project.domain.domain.product.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductLikeDto {

    //product 고유번호
    private Product productId;
    //좋아요 수
    private String likeCount;


}
