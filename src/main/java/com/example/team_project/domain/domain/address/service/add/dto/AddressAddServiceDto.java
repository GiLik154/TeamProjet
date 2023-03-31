package com.example.team_project.domain.domain.address.service.add.dto;

import lombok.Getter;

@Getter
public class AddressAddServiceDto {
    private final String name;
    private final String recipientName;
    private final String phone;
    private final String streetAddress;
    private final String detailedAddress;
    private final String zipCode;

    public AddressAddServiceDto(String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode) {
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }
}
