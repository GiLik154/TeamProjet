package com.example.team_project.exception;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(){
        super("This product is sold out");
    }
}
