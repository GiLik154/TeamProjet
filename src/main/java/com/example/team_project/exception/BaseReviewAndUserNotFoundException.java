package com.example.team_project.exception;

public class BaseReviewAndUserNotFoundException extends RuntimeException {
    public BaseReviewAndUserNotFoundException(){
        super("BaseReview or User Not Found");
    }
}
