package com.backend.spring.controller;

import com.backend.spring.models.ProjectUserEntity;
import com.backend.spring.service.ProjectUserService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project-users")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @Autowired
    public ProjectUserController(ProjectUserService projectUserService) {
        this.projectUserService = projectUserService;
    }

    @PostMapping
    public ResponseEntity<ProjectUserEntity> assignUserToProject(@RequestBody ProjectUserEntity projectUserEntity, HttpServletRequest request, HttpServletResponse response) {
        ProjectUserEntity assignedUser = projectUserService.assignUserToProject(projectUserEntity);
        return new ResponseEntity<>(assignedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userProjectId}")
    public ResponseEntity<ProjectUserEntity> updateUserProjectRole(@PathVariable Long userProjectId, @RequestBody ProjectUserEntity updatedProjectUser, HttpServletRequest request, HttpServletResponse response) {
        ProjectUserEntity updatedUser = projectUserService.updateUserProjectRole(userProjectId, updatedProjectUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectUserEntity>> getUsersAssignedToProject(@PathVariable Long projectId, HttpServletRequest request, HttpServletResponse response) {
        List<ProjectUserEntity> projectUsers = projectUserService.getUsersAssignedToProject(projectId);
        return new ResponseEntity<>(projectUsers, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectUserEntity>> getProjectsbyUserId(@PathVariable Long userId, HttpServletRequest request, HttpServletResponse response) {
        List<ProjectUserEntity> usersProject = projectUserService.getProjectsbyUserId(userId);
        return new ResponseEntity<>(usersProject, HttpStatus.OK);
    }

    @GetMapping("/{userProjectId}")
    public ResponseEntity<ProjectUserEntity> getUserProjectById(@PathVariable Long userProjectId, HttpServletRequest request, HttpServletResponse response) {
        Optional<ProjectUserEntity> projectUser = projectUserService.getUserProjectById(userProjectId);
        return projectUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{userProjectId}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable Long userProjectId, HttpServletRequest request, HttpServletResponse response) {
        projectUserService.removeUserFromProject(userProjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
