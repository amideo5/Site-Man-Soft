package com.backend.spring.exceptions;

public class ProjectUserAlreadyExistException extends Exception{
    public ProjectUserAlreadyExistException(Long id){
        super("Project User with id : " + id + " already exist");
    }

}