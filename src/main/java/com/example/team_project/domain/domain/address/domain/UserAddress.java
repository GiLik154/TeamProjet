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
    /** 주소를 가지고 있는 유저의 정보 */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    /** 주소 저장 이름 */
    private String name;
    /** 받는이 이름 */
    private String recipientName;
    /** 받는이 번호 */
    private String phone;
    /** 큰 주소 */
    private String streetAddress;
    /** 상세 주소 */
    private String detailedAddress;
    /** 우편 번호 */
    private String zipCode;

    protected UserAddress() {
    }

    /**
     * 유저 주소를 처음 생성하는 생성자
     * */
    public UserAddress(User user, String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode) {
        this.user = user;
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }

    /**
     * 업데이트 메소드
     * (유저는 업데이트 하지 못함)
     * */
    public void updateInfo(String name, String recipientName, String phone, String streetAddress, String detailedAddress, String zipCode){
        this.name = name;
        this.recipientName = recipientName;
        this.phone = phone;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.zipCode = zipCode;
    }

    /**
     *  회원가입 시 주소를 입력하는 생성자
     */
    public UserAddress(String zipCode, String streetAddress, String detailedAddress, User user) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailedAddress = detailedAddress;
        this.user = user;
    }
}
