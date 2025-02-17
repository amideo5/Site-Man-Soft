package com.backend.spring.controller;

import com.backend.spring.models.ResourceAllocationEntity;
import com.backend.spring.service.ResourceAllocationService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resource-allocations")
public class ResourceAllocationController {

    private final ResourceAllocationService resourceAllocationService;

    @Autowired
    public ResourceAllocationController(ResourceAllocationService resourceAllocationService) {
        this.resourceAllocationService = resourceAllocationService;
    }

    @PostMapping
    public ResponseEntity<ResourceAllocationEntity> createResourceAllocation(@RequestBody ResourceAllocationEntity resourceAllocation, HttpServletRequest request, HttpServletResponse response) {
        ResourceAllocationEntity createdResourceAllocation = resourceAllocationService.createResourceAllocation(resourceAllocation);
        return new ResponseEntity<>(createdResourceAllocation, HttpStatus.CREATED);
    }

    @PutMapping("/{allocationId}")
    public ResponseEntity<ResourceAllocationEntity> updateResourceAllocation(@PathVariable Long allocationId, @RequestBody ResourceAllocationEntity updatedResourceAllocation, HttpServletRequest request, HttpServletResponse response) {
        ResourceAllocationEntity updatedEntity = resourceAllocationService.updateResourceAllocation(allocationId, updatedResourceAllocation);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @GetMapping("/{allocationId}")
    public ResponseEntity<ResourceAllocationEntity> getResourceAllocationById(@PathVariable Long allocationId, HttpServletRequest request, HttpServletResponse response) {
        Optional<ResourceAllocationEntity> allocation = resourceAllocationService.getResourceAllocationById(allocationId);
        return allocation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<ResourceAllocationEntity>> getAllResourceAllocations(HttpServletRequest request, HttpServletResponse response) {
        List<ResourceAllocationEntity> allocations = resourceAllocationService.getAllResourceAllocations();
        return new ResponseEntity<>(allocations, HttpStatus.OK);
    }

    @DeleteMapping("/{allocationId}")
    public ResponseEntity<Void> deleteResourceAllocation(@PathVariable Long allocationId, HttpServletRequest request, HttpServletResponse response) {
        resourceAllocationService.deleteResourceAllocation(allocationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResourceAllocationEntity>> getTasksByUserId(@PathVariable Long userId) {
        List<ResourceAllocationEntity> tasks = resourceAllocationService.getResourceAllocationByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
