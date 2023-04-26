package com.example.team_project.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException() {super("This payment couldn't be found");}
}
