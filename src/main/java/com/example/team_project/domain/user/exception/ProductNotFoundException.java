package com.example.team_project.domain.user.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("This product does not exist");
    }
}
