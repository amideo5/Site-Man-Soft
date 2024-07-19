package com.backend.spring.models;

import com.backend.spring.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class UserEntity {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
//    private String userType;
    @Enumerated(EnumType.STRING)
    private UserType userType;

}
