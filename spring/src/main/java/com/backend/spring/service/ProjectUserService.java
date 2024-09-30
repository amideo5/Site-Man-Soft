package com.backend.spring.service;

import com.backend.spring.exceptions.ProjectUserAlreadyExistException;
import com.backend.spring.exceptions.ProjectUserNotFoundException;
import com.backend.spring.models.ProjectUserEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectUserService {

    ProjectUserEntity getProjectUserByProjectUserName(String projectUserName) throws ProjectUserNotFoundException;
    String createProjectUser(ProjectUserEntity projectUser) throws ProjectUserAlreadyExistException;
    String updateProjectUser(String projectUserName, ProjectUserEntity projectUser) throws ProjectUserNotFoundException;
    List<ProjectUserEntity> getProjectUsers();
    Optional<ProjectUserEntity> getProjectUserById(Long id) throws ProjectUserNotFoundException;
}
