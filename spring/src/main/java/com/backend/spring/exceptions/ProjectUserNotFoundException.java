package com.backend.spring.exceptions;

public class ProjectUserNotFoundException extends Exception{
    public ProjectUserNotFoundException(Long id){
        super("Project User with id : " + id + " not found");
    }
}
