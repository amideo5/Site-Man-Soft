package com.backend.spring.exceptions;

public class ProjectUserAlreadyExistException extends Exception{
    public ProjectUserAlreadyExistException(String name){
        super("Project User with name : " + name + " already exist");
    }

}