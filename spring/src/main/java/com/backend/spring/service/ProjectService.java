package com.backend.spring.service;

import com.backend.spring.models.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    ProjectEntity createProject(ProjectEntity project);
    ProjectEntity updateProject(Long projectId, ProjectEntity updatedProject);
    List<ProjectEntity> getAllProjects();
    Optional<ProjectEntity> getProjectById(Long projectId);
    void deleteProject(Long projectId);
}
