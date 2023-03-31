package com.example.team_project.exception;

public class ExpiredCouponException extends RuntimeException{

    public ExpiredCouponException(){
        super("Expired Coupon Exception");
    }
}
