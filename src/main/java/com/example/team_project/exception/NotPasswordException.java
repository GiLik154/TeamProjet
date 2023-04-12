package com.example.team_project.exception;

public class NotPasswordException extends RuntimeException {
    public NotPasswordException(String message){
        super(message);
    }
}
