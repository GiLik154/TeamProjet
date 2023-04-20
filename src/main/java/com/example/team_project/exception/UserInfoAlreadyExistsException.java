package com.example.team_project.exception;

public class UserInfoAlreadyExistsException extends RuntimeException {
    public UserInfoAlreadyExistsException(String errorMessage) { super(errorMessage); }
}
