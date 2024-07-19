package com.backend.spring.service;

import com.backend.spring.exceptions.UserAlreadyExistException;
import com.backend.spring.exceptions.UserNotFoundException;
import com.backend.spring.models.UserEntity;
import com.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String createUser(UserEntity user) throws UserAlreadyExistException {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException(user.getUsername());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setUserType(user.getUserType());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
        return "User Created";
    }

    @Override
    public UserEntity getUserByUserName(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null) {
            throw new UserNotFoundException(username);
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public String updateUser(String username, UserEntity user) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null) {
            throw new UserNotFoundException(username);
        }
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setUsername(user.getUsername());
        userEntity.setUserType(user.getUserType());
        userEntity.setPassword(user.getPassword());
        userRepository.save(userEntity);
        return "User Updated";
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public String signInUser(UserEntity user) {
        if(user.getUsername()==null||user.getPassword()==null||user.getUserType()==null)
            return "Bad credentials";
        UserEntity user1 = userRepository.findByUsername(user.getUsername());
        if(user.getPassword().equals(user1.getPassword()) && user.getUserType().equals(user1.getUserType()))
            return "Successful";
        else
            return "Bad Credentials";
    }
}
