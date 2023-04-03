package com.example.team_project.exception;

public class InvalidQuantityException extends RuntimeException{

    public InvalidQuantityException(){
        super("Please enter the correct number");
    }
}
