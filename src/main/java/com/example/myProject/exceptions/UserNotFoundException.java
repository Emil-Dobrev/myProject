package com.example.myProject.exceptions;

public class UserNotFoundException extends  RuntimeException{

    public UserNotFoundException(String errorMessage) {
       super(errorMessage);
    }

}
