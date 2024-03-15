package com.backend.spring.service;

import com.backend.spring.exceptions.EmployeeAlreadyExistException;
import com.backend.spring.exceptions.EmployeeNotFoundException;
import com.backend.spring.models.EmployeeEntity;
import com.backend.spring.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String createEmployee(EmployeeEntity employee) throws EmployeeAlreadyExistException {
        if(employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null){
            throw new EmployeeAlreadyExistException(employee.getEmployeeId());
        }
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employee.getName());
        employeeEntity.setPhone(employee.getPhone());
        employeeEntity.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employeeEntity.setEmployeeId(employee.getEmployeeId());
        employeeRepository.save(employeeEntity);
        return "Employee Created";
    }

    @Override
    public EmployeeEntity getEmployeeByEmployeeId(String employeeId) throws EmployeeNotFoundException {
        if(employeeRepository.findByEmployeeId(employeeId) == null){
            throw new EmployeeNotFoundException(employeeId);
        }
        return employeeRepository.findByEmployeeId(employeeId);
    }

    @Override
    public String updateEmployee(String employeeId, EmployeeEntity employee) throws EmployeeNotFoundException {
        if(employeeRepository.findByEmployeeId(employeeId) == null) {
            throw new EmployeeNotFoundException(employeeId);
        }
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        employeeEntity.setName(employee.getName());
        employeeEntity.setEmployeeId(employee.getEmployeeId());
        employeeEntity.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        employeeEntity.setPhone(employee.getPhone());
        employeeRepository.save(employeeEntity);
        return "Employee Updated";
    }

    @Override
    public List<EmployeeEntity> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public String clockInOutEmployee(String employeeId, String password) {
        if(employeeId==null||password==null)
            return "Bad credentials";
        EmployeeEntity employee1 = employeeRepository.findByEmployeeId(employeeId);
        if (employee1.getStatus() == null){
            employee1.setStatus("OUT");
        }
        if(employee1.getStatus().equals("IN")){
            employeeRepository.updateStatusById("OUT", employeeId);
            return "CLOCK OUT";
        }
        else if(employee1.getStatus().equals("OUT")){
            employeeRepository.updateStatusById("IN", employeeId);
            return "CLOCK IN";
        }
        return "Bad credentials";
    }

    @Override
    public String getName(String employeeId) throws EmployeeNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        if(employeeEntity == null){
            throw new EmployeeNotFoundException(employeeId);
        }
        return employeeEntity.getName();
    }
}
