package com.example.team_project.enums;

public enum OrderStatus {

    /**
     * 주문완료
     **/
    ORDERED("ORDERED"),

    /**
     * 결제 완료
     **/
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),

    /**
     * 배송 준비 중
     **/
    PREPARING_FOR_SHIPPING("PREPARING_FOR_SHIPPING"),

    /**
     * 배송중
     **/
    SHIPPED("SHIPPED"),

    /**
     * 배송 완료
     **/
    DELIVERED("DELIVERED"),

    /**
     * 주문 취소
     **/
    CANCELED("CANCELED");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}
