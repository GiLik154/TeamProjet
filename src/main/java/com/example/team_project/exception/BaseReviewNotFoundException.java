package com.example.team_project.exception;

public class BaseReviewNotFoundException extends RuntimeException {
    public BaseReviewNotFoundException(){
        super("BaseReview Not Exist");
    }
}
