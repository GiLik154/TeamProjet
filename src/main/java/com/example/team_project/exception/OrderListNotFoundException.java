package com.example.team_project.exception;

public class OrderListNotFoundException extends RuntimeException{

    public OrderListNotFoundException(){
        super("This order list does not exist");
    }
}
