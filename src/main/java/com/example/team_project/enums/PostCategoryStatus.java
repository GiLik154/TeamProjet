package com.example.team_project.enums;

public enum PostCategoryStatus {
    PRODUCT_INQUIRY("ProductInquiry"),
    SHIPPING_INQUIRY("ShippingInquiry"),
    EXCHANGE_RETURN_INQUIRY("ExchangeReturnInquiry"),
    PAYMENT_INQUIRY("PaymentInquiry"),
    DISCOUNT_COUPON_INQUIRY("DiscountCouponInquiry"),
    ETC_INQUIRY("EtcInquiry");

    private final String name;

    PostCategoryStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
