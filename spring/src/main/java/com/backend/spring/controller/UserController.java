package com.backend.spring.controller;

import com.backend.spring.dto.LoginRequest;
import com.backend.spring.dto.LoginResponse;
import com.backend.spring.models.UserEntity;
import com.backend.spring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


import com.backend.spring.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          JwtTokenProvider jwtTokenProvider,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity, HttpServletRequest request, HttpServletResponse response) {
        // CSRF token will be checked automatically by Spring Security
        UserEntity createdUser = userService.createUser(userEntity);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long userId, @RequestBody UserEntity updatedUser, HttpServletRequest request, HttpServletResponse response) {
        UserEntity updatedUserEntity = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(updatedUserEntity, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId, HttpServletRequest request, HttpServletResponse response) {
        Optional<UserEntity> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<UserEntity>> getUsersByTenant(@PathVariable Long tenantId, HttpServletRequest request, HttpServletResponse response) {
        List<UserEntity> users = userService.getUsersByTenant(tenantId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId, HttpServletRequest request, HttpServletResponse response) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = userService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            e.printStackTrace();
            // Optionally log the exception
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<UserEntity> getUserByUserName(@PathVariable String username, HttpServletRequest request, HttpServletResponse response) {
        Optional<UserEntity> user = userService.getUserByUserName(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/manager/{managerId}/employees")
    public List<UserEntity> getEmployeesUnderManager(@PathVariable Long managerId) {
        return userService.getEmployeesUnderManager(managerId);
    }
}
