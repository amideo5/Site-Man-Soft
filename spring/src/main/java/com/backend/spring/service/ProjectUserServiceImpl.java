package com.backend.spring.service;

import com.backend.spring.exceptions.ProjectUserAlreadyExistException;
import com.backend.spring.exceptions.ProjectUserNotFoundException;
import com.backend.spring.models.ProjectUserEntity;
import com.backend.spring.repository.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectUserServiceImpl implements ProjectUserService{

    @Autowired
    private final ProjectUserRepository projectUserRepository;

    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

//    @Override
//    public ProjectUserEntity getProjectUserByProjectUserName(String projectUserName) throws ProjectUserNotFoundException {
//        if (projectUserRepository.findByProjectUserName(projectUserName) == null) {
//            throw new ProjectUserNotFoundException(projectUserName);
//        }
//        return projectUserRepository.findByProjectUserName(projectUserName);
//    }

    @Override
    public String createProjectUser(ProjectUserEntity projectUser) throws ProjectUserAlreadyExistException {
//        if(projectUserRepository.findById(projectUser.getUserProjectId()).isPresent()){
//            throw new ProjectUserAlreadyExistException(projectUser.getUserProjectId().toString());
//        }
        ProjectUserEntity projectUserEntity = new ProjectUserEntity();
        projectUserEntity.setUser(projectUser.getUser());
        projectUserEntity.setProject(projectUser.getProject());
        projectUserEntity.setRole(projectUser.getRole());
        projectUserRepository.save(projectUser);
        return "Project User Created";
    }

    @Override
    public String updateProjectUser(Long id, ProjectUserEntity projectUser) throws ProjectUserNotFoundException {
        Optional<ProjectUserEntity> projectUserEntityOptional = projectUserRepository.findById(id);

        if (projectUserEntityOptional.isEmpty()) {
            throw new ProjectUserNotFoundException(id.toString());
        }
        ProjectUserEntity projectUserEntity = projectUserEntityOptional.get();
        projectUserEntity.setUser(projectUser.getUser());
        projectUserEntity.setProject(projectUser.getProject());
        projectUserEntity.setRole(projectUser.getRole());
        projectUserRepository.save(projectUserEntity);
        return "Project User Updated";
    }


    @Override
    public List<ProjectUserEntity> getProjectUsers() {
        return projectUserRepository.findAll();
    }

    @Override
    public Optional<ProjectUserEntity> getProjectUserById(Long id) throws ProjectUserNotFoundException {
        return projectUserRepository.findById(id);
    }
}
