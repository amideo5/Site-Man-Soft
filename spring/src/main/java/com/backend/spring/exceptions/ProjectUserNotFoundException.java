package com.backend.spring.exceptions;

public class ProjectUserNotFoundException extends Exception{
    public ProjectUserNotFoundException(String name){
        super("Project User with name : " + name + " not found");
    }
}
