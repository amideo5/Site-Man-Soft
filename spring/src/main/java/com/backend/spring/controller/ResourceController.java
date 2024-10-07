package com.backend.spring.controller;


import com.backend.spring.exceptions.ResourceNotFoundException;
import com.backend.spring.models.ResourceEntity;
import com.backend.spring.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/getResources")
    public ResponseEntity<?> getResources(){
        List<ResourceEntity> allResources = resourceService.getResources();
        return ResponseEntity.status(HttpStatus.OK).body(allResources);
    }

    @GetMapping("/getResourceById/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable Long id){
        try{
            Optional<ResourceEntity> resource = resourceService.getResourceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getResourceByResourceName/{resourceName}")
    public ResponseEntity<?> getResourceByResourceName(@PathVariable String resourceName){
        try{
            Optional<ResourceEntity> resource = resourceService.getResourceByResourceName(resourceName);
            return ResponseEntity.status(HttpStatus.OK).body(resource);
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/createResource")
    public ResponseEntity<?> createResource(@RequestBody ResourceEntity resource){
        String createResourceResult = resourceService.createResource(resource);
        return ResponseEntity.status(HttpStatus.OK).body(createResourceResult);
    }

    @PutMapping("/updateResource/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody ResourceEntity resource){
        try{
            String updatedResourceResult = resourceService.updateResource(id, resource);
            return ResponseEntity.status(HttpStatus.OK).body(updatedResourceResult);
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
