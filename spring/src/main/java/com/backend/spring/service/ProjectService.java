package com.backend.spring.service;

import com.backend.spring.exceptions.ProjectAlreadyExistException;
import com.backend.spring.exceptions.ProjectNotFoundException;
import com.backend.spring.models.ProjectEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    ProjectEntity getProjectByProjectName(String projectName) throws ProjectNotFoundException;
    String createProject(ProjectEntity project) throws ProjectAlreadyExistException;
    String updateProject(String projectName, ProjectEntity project) throws ProjectNotFoundException;
    List<ProjectEntity> getProjects();
    Optional<ProjectEntity> getProjectById(Long id) throws ProjectNotFoundException;
}
