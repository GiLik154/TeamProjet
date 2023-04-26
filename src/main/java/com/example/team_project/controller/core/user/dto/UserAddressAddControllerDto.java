package com.example.team_project.controller.core.user.dto;

import com.example.team_project.domain.domain.address.service.add.dto.AddressAddServiceDto;

public class UserAddressAddControllerDto {
    /**
     * 주소 저장 이름
     */
    private final String name;
    /**
     * 받는이 이름
     */
    private final String recipientName;
    /**
     * 받는이 번호
     */
    private final String phone;
    /**
     * 큰 주소
     */
    private final String streetAddress;
    /**
     * 상세 주소
     */
    private final String detailedAddress;
    /**
     * 우편 번호
     */
    private final String zipCode;

    public UserAddressAddControllerDto(String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode) {
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }

    public AddressAddServiceDto convertServiceDto() {
        return new AddressAddServiceDto(this.name, this.recipientName, this.phone, this.streetAddress, this.detailedAddress, this.zipCode);
    }
}
