package com.example.team_project.domain.domain.shop.seller.service.dto;

import lombok.Getter;

@Getter
public class SellerJoinDto {

    private Long id;
    //판매자 아이디
    private String ownerId;
    //비밀번호
    private String password;
    //판매자 이름
    private String ownerName;
    //판매자 핸드폰 번호
    private String phoneNumber;


    public SellerJoinDto(Long id) {
        this.id = id;
    }

    public SellerJoinDto(String ownerId, String password, String ownerName, String phoneNumber) {
        this.ownerId = ownerId;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }

    public SellerJoinDto() {

    }
}
