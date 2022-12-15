package com.example.myProject.exceptions;

public class EmailAlreadyTaken extends RuntimeException{

    public EmailAlreadyTaken(String errorMessage) {
        super(errorMessage);
    }
}
