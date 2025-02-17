package com.backend.spring.service;

import com.backend.spring.models.ProjectUserEntity;
import com.backend.spring.repository.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    private final ProjectUserRepository projectUserRepository;

    @Autowired
    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    @Override
    public ProjectUserEntity assignUserToProject(ProjectUserEntity projectUserEntity) {
        return projectUserRepository.save(projectUserEntity);
    }

    @Override
    public ProjectUserEntity updateUserProjectRole(Long userProjectId, ProjectUserEntity updatedProjectUser) {
        Optional<ProjectUserEntity> existingAssociationOptional = projectUserRepository.findById(userProjectId);
        if (existingAssociationOptional.isPresent()) {
            ProjectUserEntity existingAssociation = existingAssociationOptional.get();
            existingAssociation.setRole(updatedProjectUser.getRole());
            return projectUserRepository.save(existingAssociation);
        } else {
            // Handle the case when the association doesn't exist
            throw new RuntimeException("User-project association not found for id: " + userProjectId);
        }
    }

    @Override
    public List<ProjectUserEntity> getUsersAssignedToProject(Long projectId) {
        return projectUserRepository.findByProjectId(projectId);
    }

    @Override
    public Optional<ProjectUserEntity> getUserProjectById(Long userProjectId) {
        return projectUserRepository.findById(userProjectId);
    }

    @Override
    public List<ProjectUserEntity> getProjectsbyUserId(Long userId) {
        return projectUserRepository.findByUserId(userId);
    }

    @Override
    public void removeUserFromProject(Long userProjectId) {
        projectUserRepository.deleteById(userProjectId);
    }
}
