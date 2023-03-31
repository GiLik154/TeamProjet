package com.example.team_project.exception;

public class NotFoundCouponException extends RuntimeException{

    public NotFoundCouponException(){
        super("Not Found Coupon");
    }
}
