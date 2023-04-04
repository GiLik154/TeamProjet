package com.example.team_project.exception;

public class NotFoundCouponException extends RuntimeException {

    public NotFoundCouponException(String errorMessage) {
        super(errorMessage);
    }
}
