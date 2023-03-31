package com.example.team_project.exception;

public class NotFoundAddressException extends RuntimeException{

    public NotFoundAddressException(){
        super("Not Found Address");
    }
}
