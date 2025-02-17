package com.backend.spring.controller;

import com.backend.spring.models.ResourceEntity;
import com.backend.spring.service.ResourceService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResponseEntity<ResourceEntity> createResource(@RequestBody ResourceEntity resourceEntity, HttpServletRequest request, HttpServletResponse response) {
        ResourceEntity createdResource = resourceService.createResource(resourceEntity);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @PutMapping("/{resourceId}")
    public ResponseEntity<ResourceEntity> updateResource(@PathVariable Long resourceId, @RequestBody ResourceEntity updatedResource, HttpServletRequest request, HttpServletResponse response) {
        ResourceEntity updatedResourceEntity = resourceService.updateResource(resourceId, updatedResource);
        return new ResponseEntity<>(updatedResourceEntity, HttpStatus.OK);
    }

    @GetMapping("/{resourceId}")
    public ResponseEntity<ResourceEntity> getResourceById(@PathVariable Long resourceId, HttpServletRequest request, HttpServletResponse response) {
        Optional<ResourceEntity> resource = resourceService.getResourceById(resourceId);
        return resource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<ResourceEntity>> getAllResources(HttpServletRequest request, HttpServletResponse response) {
        List<ResourceEntity> resources = resourceService.getAllResources();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long resourceId, HttpServletRequest request, HttpServletResponse response) {
        resourceService.deleteResource(resourceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
