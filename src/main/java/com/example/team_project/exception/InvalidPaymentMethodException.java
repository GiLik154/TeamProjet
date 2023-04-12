package com.example.team_project.exception;

public class InvalidPaymentMethodException extends RuntimeException{

    public InvalidPaymentMethodException(){
        super("This payment method is not valid");
    }
}
