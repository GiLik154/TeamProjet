package com.example.team_project.exception;

public class InvalidAddressException extends RuntimeException{

    public InvalidAddressException(){
        super("This address is invalid");
    }
}
