package com.backend.spring.controller;

import com.backend.spring.exceptions.ProjectAlreadyExistException;
import com.backend.spring.exceptions.ProjectNotFoundException;
import com.backend.spring.exceptions.UserNotFoundException;
import com.backend.spring.models.ProjectEntity;
import com.backend.spring.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(path = "/getProjects")
    public ResponseEntity<?> getUsers(){
        List<ProjectEntity> projects = projectService.getProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping(path = "/getProject/{projectName}")
    public ResponseEntity<?> getProject(@PathVariable String projectName) throws ProjectNotFoundException {
        try{
            ProjectEntity project = projectService.getProjectByProjectName(projectName);
            return ResponseEntity.status(HttpStatus.OK).body(project);
        }
        catch (ProjectNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getProjectById/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) throws ProjectNotFoundException {
        try{
            Optional<ProjectEntity> project = projectService.getProjectById(id);
            return ResponseEntity.status(HttpStatus.OK).body(project);
        }
        catch (ProjectNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/createProject")
    public ResponseEntity<?> createProject(@RequestBody ProjectEntity project) throws ProjectAlreadyExistException {
        try {
            String createProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.OK).body(createProject);
        }catch (ProjectAlreadyExistException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/updateProject/{projectName}")
    public ResponseEntity<?> updateProject(@PathVariable String projectName, @RequestBody ProjectEntity project) throws UserNotFoundException {
        try {
            String updateProject = projectService.updateProject(projectName, project);
            return ResponseEntity.status(HttpStatus.OK).body(updateProject);
        }
        catch (ProjectNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
