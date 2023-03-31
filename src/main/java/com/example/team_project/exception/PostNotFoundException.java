package com.example.team_project.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {

        super("Invalid Post");
    }
}
