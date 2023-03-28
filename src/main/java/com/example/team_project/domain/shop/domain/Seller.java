 package com.example.team_project.domain.shop.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="seller")
@Getter
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //판매자 아이디
    @Column
    private String ownerId;

    //비밀번호
    @Column
    private String password;

    //판매자 이름
    @Column
    private String ownerName;

    //판매자 핸드폰 번호
    @Column
    private String phoneNumber;


    public Seller(String ownerId, String password, String ownerName, String phoneNumber) {
        this.ownerId = ownerId;
        this.password = password;
        this.ownerName = ownerName;
        this.phoneNumber = phoneNumber;
    }


    public Seller() {

    }


}