package com.backend.spring.exceptions;

public class ProjectAlreadyExistException extends Exception{
    public ProjectAlreadyExistException(String name){
        super("Project with name : " + name + " already exist");
    }

}
