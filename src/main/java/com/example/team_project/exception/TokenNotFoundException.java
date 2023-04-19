package com.example.team_project.exception;

public class TokenNotFoundException extends RuntimeException{


    public TokenNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
