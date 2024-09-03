package com.backend.spring.controller;

import com.backend.spring.exceptions.UserAlreadyExistException;
import com.backend.spring.exceptions.UserNotFoundException;
import com.backend.spring.models.UserEntity;
import com.backend.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/getUsers")
    public ResponseEntity<?> getUsers(){
        List<UserEntity> users = userService.getUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(path = "/getUser/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) throws UserNotFoundException {
        try{
            UserEntity user = userService.getUserByUserName(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) throws UserNotFoundException {
        try{
            Optional<UserEntity> user = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) throws UserAlreadyExistException {
        try {
            String createUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(createUser);
        }catch (UserAlreadyExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/updateUser/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity user) throws UserNotFoundException {
        try {
            String updateUser = userService.updateUser(username, user);
            return ResponseEntity.status(HttpStatus.OK).body(updateUser);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserEntity user){
        try {
            String sign = userService.signInUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(sign);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}