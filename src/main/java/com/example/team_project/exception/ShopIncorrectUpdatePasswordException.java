package com.example.team_project.exception;

public class ShopIncorrectUpdatePasswordException extends RuntimeException{

    public ShopIncorrectUpdatePasswordException(String errorMessage){
        super(errorMessage);
    }
}
