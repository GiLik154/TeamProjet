package com.example.team_project.exception;

public class PaymentNotFountException extends RuntimeException {
    PaymentNotFountException() {super("This payment couldn't be found");}
}
