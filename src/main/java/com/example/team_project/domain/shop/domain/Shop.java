package com.example.team_project.domain.shop.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Getter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_name")
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



}