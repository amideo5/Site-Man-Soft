package com.backend.spring.service;

import com.backend.spring.exceptions.ProjectAlreadyExistException;
import com.backend.spring.exceptions.ProjectNotFoundException;
import com.backend.spring.models.ProjectEntity;
import com.backend.spring.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectEntity getProjectByProjectName(String projectName) throws ProjectNotFoundException {
        if (projectRepository.findByProjectName(projectName) == null) {
            throw new ProjectNotFoundException(projectName);
        }
        return projectRepository.findByProjectName(projectName);
    }

    @Override
    public String createProject(ProjectEntity project) throws ProjectAlreadyExistException {
        if(projectRepository.findByProjectName(project.getProjectName()) != null){
            throw new ProjectAlreadyExistException(project.getProjectName());
        }
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectName(project.getProjectName());
        projectEntity.setStartDate(project.getStartDate());
        projectEntity.setEndDate(project.getEndDate());
        projectEntity.setStatus(project.getStatus());
        projectEntity.setBudget(project.getBudget());
        projectRepository.save(projectEntity);
        return "Project Created";
    }

    @Override
    public String updateProject(String projectName, ProjectEntity project) throws ProjectNotFoundException {
        if (projectRepository.findByProjectName(projectName) == null) {
            throw new ProjectNotFoundException(projectName);
        }
        ProjectEntity projectEntity = projectRepository.findByProjectName(projectName);
        projectEntity.setProjectName(project.getProjectName());
        projectEntity.setStartDate(project.getStartDate());
        projectEntity.setEndDate(project.getEndDate());
        projectEntity.setStatus(project.getStatus());
        projectEntity.setBudget(project.getBudget());
        projectRepository.save(projectEntity);
        return "Project Updated";
    }

    @Override
    public List<ProjectEntity> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<ProjectEntity> getProjectById(Long id) throws ProjectNotFoundException {
        return projectRepository.findById(id);
    }
}
