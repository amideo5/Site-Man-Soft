package com.backend.spring.service;

import com.backend.spring.exceptions.ResourceNotFoundException;
import com.backend.spring.models.ResourceEntity;
import com.backend.spring.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }


    @Override
    public List<ResourceEntity> getResources() {
        return null;
    }

    @Override
    public Optional<ResourceEntity> getResourceById(Long id) throws ResourceNotFoundException {
        return Optional.empty();
    }

    @Override
    public Optional<ResourceEntity> getResourceByResourceName(String resourceName) throws ResourceNotFoundException {
        return Optional.empty();
    }

    @Override
    public Integer getQuantity(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Integer getAvailableQuantity(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Integer getAllocatedToQuantity(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public String createResource(ResourceEntity resource) {
        return null;
    }

    @Override
    public String updateResource(Long id, ResourceEntity resource) throws ResourceNotFoundException {
        return null;
    }
}
