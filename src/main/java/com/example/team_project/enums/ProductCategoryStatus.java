package com.example.team_project.enums;



public enum ProductCategoryStatus {
    TOP("상의"),
    BOTTOM("하의"),
    SHOES("신발");

    private final String category;

    ProductCategoryStatus(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

}
