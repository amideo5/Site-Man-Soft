package com.backend.spring.service;

import com.backend.spring.dto.LoginRequest;
import com.backend.spring.dto.LoginResponse;
import com.backend.spring.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Creates a new user.
     *
     * @param userEntity the UserEntity containing user details
     * @return the created UserEntity
     */
    UserEntity createUser(UserEntity userEntity);

    /**
     * Updates an existing user.
     *
     * @param userId the ID of the user to be updated
     * @param updatedUser the updated UserEntity with new values
     * @return the updated UserEntity
     */
    UserEntity updateUser(Long userId, UserEntity updatedUser);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the UserEntity if found, otherwise empty
     */
    Optional<UserEntity> getUserById(Long userId);

    /**
     * Retrieves all users belonging to a specific tenant.
     *
     * @param tenantId the ID of the tenant
     * @return a list of UserEntities belonging to the tenant
     */
    List<UserEntity> getUsersByTenant(Long tenantId);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted
     */
    void deleteUser(Long userId);

//    UserEntity login(String username, String password);

    LoginResponse login(LoginRequest loginRequest);

    /**
     * Retrieves a user by their ID.
     *
     * @param username the ID of the user
     * @return an Optional containing the UserEntity if found, otherwise empty
     */
    Optional<UserEntity> getUserByUserName(String username);
}
