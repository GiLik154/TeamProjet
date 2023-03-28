package com.example.team_project.domain.product.service.dto;

import com.example.team_project.domain.shop.domain.Shop;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
public class ProductDto {

    //product 고유번호
    private Long id;
    //판매자 고유번호
    private Shop shopId;
    //품목이름
    private String productName;
    //품목이미지
    private String productImage;
    //상세설명
    private String productDescription;

}
