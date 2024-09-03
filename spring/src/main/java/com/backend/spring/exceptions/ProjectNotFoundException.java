package com.backend.spring.exceptions;

public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException(String name){
        super("Project with name : " + name + " not found");
    }
}
