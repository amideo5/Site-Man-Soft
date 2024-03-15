package com.backend.spring.exceptions;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(String employeeId){
        super("Employee with employee Id: " + employeeId + " not found");
    }
}