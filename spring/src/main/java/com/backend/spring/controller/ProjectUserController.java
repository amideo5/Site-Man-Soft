package com.backend.spring.controller;

import com.backend.spring.exceptions.ProjectUserNotFoundException;
import com.backend.spring.models.ProjectUserEntity;
import com.backend.spring.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projectUser")
@CrossOrigin(origins = "*")
public class ProjectUserController {

    @Autowired
    ProjectUserService projectUserService;

    @GetMapping(path = "/getProjectUsers")
    public ResponseEntity<?> getProjectUsers(){
        List<ProjectUserEntity> projectUsers = projectUserService.getProjectUsers();
        return ResponseEntity.status(HttpStatus.OK).body(projectUsers);
    }

    @GetMapping(path = "/getProjectUserById/{id}")
    public ResponseEntity<?> getProjectUserById(@PathVariable Long id) {
        try {
            Optional<ProjectUserEntity> projectUser = projectUserService.getProjectUserById(id);
            if (projectUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(projectUser);
            } else {
                throw new ProjectUserNotFoundException(id);
            }
        } catch (ProjectUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(path = "/createProjectUser")
    public ResponseEntity<?> createProjectUser(@RequestBody ProjectUserEntity projectUser) {
        String response = projectUserService.createProjectUser(projectUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/updateProjectUser/{id}")
    public ResponseEntity<?> updateProjectUser(@PathVariable Long id, @RequestBody ProjectUserEntity projectUser) {
        try {
            String response = projectUserService.updateProjectUser(id, projectUser);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ProjectUserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
