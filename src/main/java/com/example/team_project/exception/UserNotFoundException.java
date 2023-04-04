package com.example.team_project.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("This user could not be found");
    }

    public UserNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
