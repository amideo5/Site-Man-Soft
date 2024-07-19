package com.backend.spring.service;

import com.backend.spring.exceptions.UserAlreadyExistException;
import com.backend.spring.exceptions.UserNotFoundException;
import com.backend.spring.models.UserEntity;

import java.util.List;

public interface UserService {

    String createUser(UserEntity user) throws UserAlreadyExistException;
    UserEntity getUserByUserName(String username) throws UserNotFoundException;
    String updateUser(String username, UserEntity user) throws UserNotFoundException;
    List<UserEntity> getUsers();
    String signInUser(UserEntity user);
}
