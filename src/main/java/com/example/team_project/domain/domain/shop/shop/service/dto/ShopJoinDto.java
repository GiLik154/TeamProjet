package com.example.team_project.domain.domain.shop.shop.service.dto;

import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

@Getter
public class ShopJoinDto {


    // 가게 이름
    private final String shopName;
    // 가게 주소
    private final String shopAddress;
    // 사업자번호
    private final String businessRegistrationNumber;

    public ShopJoinDto(String shopName, String shopAddress, String businessRegistrationNumber) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }



}
