package com.backend.spring.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String userName){
        super("User with user name: " + userName + " not found");
    }
}