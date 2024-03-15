package com.backend.spring.exceptions;

public class EmployeeAlreadyExistException extends Exception{
    public EmployeeAlreadyExistException(String employeeId){
        super("Employee with employee Id: " + employeeId + " already exist");
    }
}