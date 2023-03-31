package com.example.team_project.domain.domain.address.domain;

import com.example.team_project.domain.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String name;
    private String recipientName;
    private String phone;
    private String streetAddress;
    private String detailedAddress;
    private String zipCode;

    protected UserAddress() {
    }

    public UserAddress(User user, String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode) {
        this.user = user;
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }

    public void updateInfo(String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode){
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }
}
