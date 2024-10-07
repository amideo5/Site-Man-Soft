package com.backend.spring.exceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(Long id) {
        super("Resource with id : " + id + "not found");
    }

}
