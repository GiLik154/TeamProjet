package com.example.team_project.exception;

public class SellerNameNotFoundException extends RuntimeException{


    public SellerNameNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
