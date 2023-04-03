package com.example.team_project.exception;

public class CannotCancelOrderException extends RuntimeException{

    public CannotCancelOrderException(){
        super("This order cannot be canceled");
    }
}
