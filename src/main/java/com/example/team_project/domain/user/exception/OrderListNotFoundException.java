package com.example.team_project.domain.user.exception;

public class OrderListNotFoundException extends RuntimeException{

    public OrderListNotFoundException(){
        super("This order list does not exist");
    }
}
