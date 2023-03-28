package com.example.team_project.domain.domain.product.service.dto;

import com.example.team_project.domain.domain.product.domain.Product;
import lombok.Getter;

@Getter
public class ProductLikeDto {

    //productLike 고유번호
    private Long likeId;
    //product 고유번호
    private Product productId;
    //좋아요 수
    private String likeCount;


}
