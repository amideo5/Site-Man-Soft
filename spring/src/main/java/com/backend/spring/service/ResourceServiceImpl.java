package com.backend.spring.service;

import com.backend.spring.models.ResourceEntity;
import com.backend.spring.repository.ResourceRepository;
import com.backend.spring.repository.ResourceAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ResourceServiceImpl implements ResourceService {

    private static final Logger logger = Logger.getLogger(ResourceServiceImpl.class.getName());

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;

    @Override
    @Transactional
    public ResourceEntity createResource(ResourceEntity resource) {
        logger.info("Creating resource: " + resource.getName());
        try {
            return resourceRepository.save(resource);
        } catch (Exception e) {
            logger.severe("Error creating resource: " + e.getMessage());
            throw new RuntimeException("Error creating resource", e);
        }
    }

    @Override
    @Transactional
    public ResourceEntity updateResource(Long resourceId, ResourceEntity updatedResource) {
        logger.info("Updating resource with ID: " + resourceId);
        Optional<ResourceEntity> existingResource = resourceRepository.findById(resourceId);
        if (existingResource.isPresent()) {
            ResourceEntity resource = existingResource.get();
            resource.setName(updatedResource.getName());
            resource.setTotalQuantity(updatedResource.getTotalQuantity());
            resource.setAvailableQuantity(updatedResource.getAvailableQuantity());
            resource.setTenant(updatedResource.getTenant());
            try {
                return resourceRepository.save(resource);
            } catch (Exception e) {
                logger.severe("Error updating resource: " + e.getMessage());
                throw new RuntimeException("Error updating resource", e);
            }
        } else {
            logger.warning("Resource not found with ID: " + resourceId);
            throw new RuntimeException("Resource not found");
        }
    }

    @Override
    public List<ResourceEntity> getAllResources() {
        logger.info("Fetching all resources");
        return resourceRepository.findAll();
    }

    @Override
    public Optional<ResourceEntity> getResourceById(Long resourceId) {
        logger.info("Fetching resource with ID: " + resourceId);
        return resourceRepository.findById(resourceId);
    }

    @Override
    @Transactional
    public void deleteResource(Long resourceId) {
        logger.info("Deleting resource with ID: " + resourceId);
        try {
            resourceRepository.deleteById(resourceId);
        } catch (Exception e) {
            logger.severe("Error deleting resource: " + e.getMessage());
            throw new RuntimeException("Error deleting resource", e);
        }
    }
}
