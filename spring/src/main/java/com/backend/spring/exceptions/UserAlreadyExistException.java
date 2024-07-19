package com.backend.spring.exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String userName){
        super("User with user name: " + userName + " already exist");
    }
}