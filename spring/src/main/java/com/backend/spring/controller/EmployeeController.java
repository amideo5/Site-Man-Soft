package com.backend.spring.controller;

import com.backend.spring.exceptions.EmployeeAlreadyExistException;
import com.backend.spring.exceptions.EmployeeNotFoundException;
import com.backend.spring.models.EmployeeEntity;
import com.backend.spring.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(path = "/getEmployees")
    public ResponseEntity<?> getEmployees(){
        List<EmployeeEntity> employees = employeeService.getEmployees();
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping(path = "/getEmployee/{employeeId}")
    public ResponseEntity<?> getEmployeeByEmployeeId(@PathVariable String employeeId) throws EmployeeNotFoundException {
        try{
            EmployeeEntity employee = employeeService.getEmployeeByEmployeeId(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        catch (EmployeeNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/createEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeEntity employeeDetails) throws EmployeeAlreadyExistException {
        try {
            String createEmployee = employeeService.createEmployee(employeeDetails);
            return ResponseEntity.status(HttpStatus.OK).body(createEmployee);
        }catch (EmployeeAlreadyExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/updateEmployee/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeEntity employeeDetails) throws EmployeeNotFoundException {
        try {
            String updateEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
            return ResponseEntity.status(HttpStatus.OK).body(updateEmployee);
        }
        catch (EmployeeNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/clock/{employeeId}/{password}")
    public ResponseEntity<?> clockInOutEmployee(@PathVariable String employeeId, @PathVariable String password){
        try {
            String clock = employeeService.clockInOutEmployee(employeeId,password);
            return ResponseEntity.status(HttpStatus.OK).body(clock);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/name/{employeeId}")
    public ResponseEntity<?> getName(@PathVariable String employeeId) throws EmployeeNotFoundException{
        try {
            String name = employeeService.getName(employeeId);
            return ResponseEntity.status(HttpStatus.OK).body(name);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
