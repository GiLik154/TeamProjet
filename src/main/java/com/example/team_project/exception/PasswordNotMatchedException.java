package com.example.team_project.exception;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException() {super("this password doesn't match");}
}
