package com.example.team_project.exception;

public class UserIdAlreadyExistsException extends RuntimeException {
    public UserIdAlreadyExistsException() { super("userId already exists"); }
}
