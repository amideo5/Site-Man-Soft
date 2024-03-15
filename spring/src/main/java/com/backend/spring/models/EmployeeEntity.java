package com.backend.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Date created;

    private String name;

    private String phone;

    private String employeeId;

    private String password;

    @JsonIgnore
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeEntity(Date created, String name, String phone, String employeeId, String password, String status) {
        this.created = created;
        this.name = name;
        this.phone = phone;
        this.employeeId = employeeId;
        this.password = password;
        this.status = status;
    }

    public EmployeeEntity() {
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "created=" + created +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", status='" + status + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
