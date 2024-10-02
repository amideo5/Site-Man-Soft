package com.backend.spring.controller;

import com.backend.spring.exceptions.*;
import com.backend.spring.models.ProjectEntity;
import com.backend.spring.models.ProjectUserEntity;
import com.backend.spring.service.ProjectService;
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

//    @GetMapping(path = "/getProject/{projectName}")
//    public ResponseEntity<?> getProject(@PathVariable String projectName) throws ProjectNotFoundException {
//        try{
//            ProjectEntity project = projectService.getProjectByProjectName(projectName);
//            return ResponseEntity.status(HttpStatus.OK).body(project);
//        }
//        catch (ProjectNotFoundException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @GetMapping(path = "/getProjectUserById/{id}")
    public ResponseEntity<?> getProjectUserById(@PathVariable Long id) throws ProjectUserNotFoundException {
        try{
            Optional<ProjectUserEntity> project = projectUserService.getProjectUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(project);
        }
        catch (ProjectUserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/createProjectUser")
    public ResponseEntity<?> createProjectUser(@RequestBody ProjectUserEntity projectUser) throws ProjectUserAlreadyExistException {
        try {
            String createProjectUser = projectUserService.createProjectUser(projectUser);
            return ResponseEntity.status(HttpStatus.OK).body(createProjectUser);
        }catch (ProjectUserAlreadyExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/updateProjectUser/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody ProjectUserEntity projectUser) throws ProjectUserNotFoundException {
        try {
            String updateProjectUser = projectUserService.updateProjectUser(projectId, projectUser);
            return ResponseEntity.status(HttpStatus.OK).body(updateProjectUser);
        }
        catch (ProjectUserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
