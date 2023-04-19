package com.example.team_project.exception;

public class SellerNotFoundException extends RuntimeException{


    public SellerNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
