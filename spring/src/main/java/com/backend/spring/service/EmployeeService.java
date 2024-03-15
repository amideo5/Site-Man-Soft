package com.backend.spring.service;

import com.backend.spring.exceptions.EmployeeAlreadyExistException;
import com.backend.spring.exceptions.EmployeeNotFoundException;
import com.backend.spring.models.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    String createEmployee(EmployeeEntity employee) throws EmployeeAlreadyExistException;
    EmployeeEntity getEmployeeByEmployeeId(String employeeId) throws EmployeeNotFoundException;
    String updateEmployee(String employeeId, EmployeeEntity employee) throws EmployeeNotFoundException;
    List<EmployeeEntity> getEmployees();
    String clockInOutEmployee(String employeeId, String password);
    String getName(String employeeId) throws EmployeeNotFoundException;
}
