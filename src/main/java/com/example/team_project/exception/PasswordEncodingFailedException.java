package com.example.team_project.exception;

public class PasswordEncodingFailedException extends RuntimeException{

    public PasswordEncodingFailedException(){
        super("this password encoding fail");
    }

}
