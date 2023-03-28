package com.example.team_project.domain.user.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("This user could not be found");
    }
}
