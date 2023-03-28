package com.example.team_project.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
        super("This product does not exist");
    }
}
