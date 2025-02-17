package com.backend.spring.service;

import com.backend.spring.models.ProjectEntity;
import com.backend.spring.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = Logger.getLogger(ProjectServiceImpl.class.getName());

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectEntity createProject(ProjectEntity project) {
        logger.info("Creating project: " + project.getName());
        try {
            return projectRepository.save(project);
        } catch (Exception e) {
            logger.severe("Error creating project: " + e.getMessage());
            throw new RuntimeException("Error creating project", e);
        }
    }

    @Override
    @Transactional
    public ProjectEntity updateProject(Long projectId, ProjectEntity updatedProject) {
        logger.info("Updating project with ID: " + projectId);
        Optional<ProjectEntity> existingProject = projectRepository.findById(projectId);
        if (existingProject.isPresent()) {
            ProjectEntity project = existingProject.get();
            project.setName(updatedProject.getName());
            project.setStartDate(updatedProject.getStartDate());
            project.setEndDate(updatedProject.getEndDate());
            project.setBudget(updatedProject.getBudget());
            project.setStatus(updatedProject.getStatus());
            project.setTenant(updatedProject.getTenant());
            try {
                return projectRepository.save(project);
            } catch (Exception e) {
                logger.severe("Error updating project: " + e.getMessage());
                throw new RuntimeException("Error updating project", e);
            }
        } else {
            logger.warning("Project not found with ID: " + projectId);
            throw new RuntimeException("Project not found");
        }
    }

    @Override
    public List<ProjectEntity> getAllProjects() {
        logger.info("Fetching all projects");
        return projectRepository.findAll();
    }

    @Override
    public Optional<ProjectEntity> getProjectById(Long projectId) {
        logger.info("Fetching project with ID: " + projectId);
        return projectRepository.findById(projectId);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        logger.info("Deleting project with ID: " + projectId);
        try {
            projectRepository.deleteById(projectId);
        } catch (Exception e) {
            logger.severe("Error deleting project: " + e.getMessage());
            throw new RuntimeException("Error deleting project", e);
        }
    }
}
