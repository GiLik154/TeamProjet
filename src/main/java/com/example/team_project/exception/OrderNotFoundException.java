package com.example.team_project.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(){
        super("This order does not exist");
    }
}
