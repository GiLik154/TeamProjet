package com.example.team_project.exception;

public class SellerNotFoundException extends RuntimeException{

    public SellerNotFoundException(){
        super("This seller could not be found");
    }

}
