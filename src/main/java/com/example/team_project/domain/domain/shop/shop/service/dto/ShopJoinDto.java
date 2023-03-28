package com.example.team_project.domain.domain.shop.shop.service.dto;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

@Getter
public class ShopJoinDto {

    private Long id;
    // 판매자 아이디
    private Seller seller;
    // 가게 이름
    private String shopName;
    // 가게 주소
    private String shopAddress;
    // 사업자번호
    private String businessRegistrationNumber;

    public ShopJoinDto(String shopName, String shopAddress, String businessRegistrationNumber) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public ShopJoinDto() {

    }

    public ShopJoinDto(Seller seller) {
        this.seller = seller;
    }
}
