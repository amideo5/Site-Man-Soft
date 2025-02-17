package com.backend.spring.service;

import com.backend.spring.dto.LoginRequest;
import com.backend.spring.dto.LoginResponse;
import com.backend.spring.models.UserEntity;
import com.backend.spring.repository.UserRepository;
import com.backend.spring.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }


    @Override
    public UserEntity updateUser(Long userId, UserEntity updatedUser) {
        Optional<UserEntity> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            UserEntity existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setDesignation(updatedUser.getDesignation());
            existingUser.setTeam(updatedUser.getTeam());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setEnabled(updatedUser.getEnabled());
            existingUser.setTenant(updatedUser.getTenant());
            return userRepository.save(existingUser);
        } else {
            // Handle the case when the user doesn't exist
            throw new RuntimeException("User not found for id: " + userId);
        }
    }

    @Override
    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<UserEntity> getUsersByTenant(Long tenantId) {
        return userRepository.findByTenantId(tenantId);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // Find the user by username
        Optional<UserEntity> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid credentials");
        }

        UserEntity user = userOptional.get();

        // Validate the password using PasswordEncoder (assuming the stored password is hashed)
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Check if the user is enabled
        if (!user.getEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        // Optional: Role-based logic (e.g., logging)
        switch (user.getRole()) {
            case ADMIN:
                System.out.println("Admin logged in");
                break;
            case MANAGER:
                System.out.println("Manager logged in");
                break;
            case EMPLOYEE:
                System.out.println("Employee logged in");
                break;
            default:
                throw new RuntimeException("Invalid role");
        }

        // Generate JWT token
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole().name());

        // Return the token wrapped in a LoginResponse DTO
        return new LoginResponse(token);
    }

    @Override
    public Optional<UserEntity> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
