package com.backend.spring.service;

import com.backend.spring.models.ProjectUserEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectUserService {

    /**
     * Assigns a user to a project with a specific role.
     *
     * @param projectUserEntity the ProjectUserEntity containing the user, project, and role
     * @return the created ProjectUserEntity
     */
    ProjectUserEntity assignUserToProject(ProjectUserEntity projectUserEntity);

    /**
     * Updates the role of a user in a project.
     *
     * @param userProjectId the ID of the association between the user and the project
     * @param updatedProjectUser the updated ProjectUserEntity with the new role
     * @return the updated ProjectUserEntity
     */
    ProjectUserEntity updateUserProjectRole(Long userProjectId, ProjectUserEntity updatedProjectUser);

    /**
     * Retrieves all users assigned to a project.
     *
     * @param projectId the ID of the project
     * @return a list of ProjectUserEntities representing the users assigned to the project
     */
    List<ProjectUserEntity> getUsersAssignedToProject(Long projectId);

    /**
     * Retrieves a specific user-project assignment by its ID.
     *
     * @param userProjectId the ID of the user-project assignment
     * @return an Optional containing the ProjectUserEntity if found, otherwise empty
     */
    Optional<ProjectUserEntity> getUserProjectById(Long userProjectId);

    /**
     * Retrieves a specific user-project assignment by its ID.
     *
     * @param userId the ID of the user-project assignment
     * @return an Optional containing the ProjectUserEntity if found, otherwise empty
     */
    List<ProjectUserEntity> getProjectsbyUserId(Long userId);

    /**
     * Removes a user from a project.
     *
     * @param userProjectId the ID of the user-project assignment to be deleted
     */
    void removeUserFromProject(Long userProjectId);
}
