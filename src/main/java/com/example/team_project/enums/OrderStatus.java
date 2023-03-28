package com.example.team_project.enums;

public enum OrderStatus {

    ORDERED("ORDERED"),
    PAYMENT_COMPLETED("PAYMENT_COMPLETED"),
    PREPARING_FOR_SHIPPING("PREPARING_FOR_SHIPPING"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELED("CANCELED");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
