package com.backend.spring.controller;

import com.backend.spring.models.ProjectEntity;
import com.backend.spring.service.ProjectService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectEntity projectEntity, HttpServletRequest request, HttpServletResponse response) {
        ProjectEntity createdProject = projectService.createProject(projectEntity);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable Long projectId, @RequestBody ProjectEntity updatedProject, HttpServletRequest request, HttpServletResponse response) {
        ProjectEntity updatedProjectEntity = projectService.updateProject(projectId, updatedProject);
        return new ResponseEntity<>(updatedProjectEntity, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long projectId, HttpServletRequest request, HttpServletResponse response) {
        Optional<ProjectEntity> project = projectService.getProjectById(projectId);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<ProjectEntity>> getAllProjects(HttpServletRequest request, HttpServletResponse response) {
        List<ProjectEntity> projects = projectService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId, HttpServletRequest request, HttpServletResponse response) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
