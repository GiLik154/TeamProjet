package com.example.team_project.domain.domain.shop.shop.domain;


import com.example.team_project.domain.domain.shop.seller.domain.Seller;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    //가게 이름
    @Column
    private String shopName;

    //가게 주소
    @Column
    private String shopAddress;

    //사업자번호
    @Column
    private String businessRegistrationNumber;

    public Shop() {
    }

    public Shop(Seller seller, String shopName, String shopAddress, String businessRegistrationNumber) {
        this.seller = seller;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public Shop(String shopName, String shopAddress, String businessRegistrationNumber) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public void update(String shopName, String shopAddress) {
        this.shopName = shopName;
        this.shopAddress = shopAddress;
    }
}