package com.example.team_project.exception;

public class SellerDuplicateSellerException extends RuntimeException{

    public SellerDuplicateSellerException(){
        super("This sellerId already exists");
    }
}
