package com.backend.spring.service;

import com.backend.spring.exceptions.ResourceNotFoundException;
import com.backend.spring.models.ResourceEntity;

import java.util.List;
import java.util.Optional;

public interface ResourceService {

    List<ResourceEntity> getResources();

    Optional<ResourceEntity> getResourceById(Long id) throws ResourceNotFoundException;

    Optional<ResourceEntity> getResourceByResourceName(String resourceName) throws ResourceNotFoundException;

    String createResource(ResourceEntity resource);

    String updateResource(Long id, ResourceEntity resource) throws ResourceNotFoundException;

}
