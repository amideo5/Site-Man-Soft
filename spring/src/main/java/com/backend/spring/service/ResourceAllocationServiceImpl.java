package com.backend.spring.service;

import com.backend.spring.models.ResourceAllocationEntity;
import com.backend.spring.models.ResourceEntity;
import com.backend.spring.repository.ResourceAllocationRepository;
import com.backend.spring.repository.ResourceRepository;
import com.backend.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

    private static final Logger logger = Logger.getLogger(ResourceAllocationServiceImpl.class.getName());

    @Autowired
    private ResourceAllocationRepository resourceAllocationRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public ResourceAllocationEntity createResourceAllocation(ResourceAllocationEntity resourceAllocation) {
        logger.info("Creating resource allocation for resource: " + resourceAllocation.getResource().getName());
        try {
            Optional<ResourceEntity> resource = resourceRepository.findById(resourceAllocation.getResource().getId());
            if (resource.isPresent()) {
                if (resource.get().getAvailableQuantity() >= resourceAllocation.getAllocatedQuantity()) {
                    // Update available quantity after allocation
                    resource.get().setAvailableQuantity(resource.get().getAvailableQuantity() - resourceAllocation.getAllocatedQuantity());
                    resourceRepository.save(resource.get());

                    return resourceAllocationRepository.save(resourceAllocation);
                } else {
                    logger.warning("Not enough resource available for allocation.");
                    throw new RuntimeException("Insufficient resource available.");
                }
            } else {
                logger.warning("Resource not found.");
                throw new RuntimeException("Resource not found.");
            }
        } catch (Exception e) {
            logger.severe("Error creating resource allocation: " + e.getMessage());
            throw new RuntimeException("Error creating resource allocation", e);
        }
    }

    @Override
    @Transactional
    public ResourceAllocationEntity updateResourceAllocation(Long allocationId, ResourceAllocationEntity updatedResourceAllocation) {
        logger.info("Updating resource allocation with ID: " + allocationId);
        Optional<ResourceAllocationEntity> existingAllocation = resourceAllocationRepository.findById(allocationId);
        if (existingAllocation.isPresent()) {
            ResourceAllocationEntity allocation = existingAllocation.get();
            allocation.setResource(updatedResourceAllocation.getResource());
            allocation.setUser(updatedResourceAllocation.getUser());
            allocation.setAllocatedQuantity(updatedResourceAllocation.getAllocatedQuantity());
            allocation.setReturnDate(updatedResourceAllocation.getReturnDate());

            try {
                return resourceAllocationRepository.save(allocation);
            } catch (Exception e) {
                logger.severe("Error updating resource allocation: " + e.getMessage());
                throw new RuntimeException("Error updating resource allocation", e);
            }
        } else {
            logger.warning("Resource Allocation not found with ID: " + allocationId);
            throw new RuntimeException("Resource Allocation not found");
        }
    }

    @Override
    public List<ResourceAllocationEntity> getAllResourceAllocations() {
        logger.info("Fetching all resource allocations");
        return (List<ResourceAllocationEntity>) resourceAllocationRepository.findAll();
    }

    @Override
    public Optional<ResourceAllocationEntity> getResourceAllocationById(Long allocationId) {
        logger.info("Fetching resource allocation with ID: " + allocationId);
        return resourceAllocationRepository.findById(allocationId);
    }

    @Override
    @Transactional
    public void deleteResourceAllocation(Long allocationId) {
        logger.info("Deleting resource allocation with ID: " + allocationId);
        try {
            Optional<ResourceAllocationEntity> allocation = resourceAllocationRepository.findById(allocationId);
            if (allocation.isPresent()) {
                ResourceEntity resource = allocation.get().getResource();
                resource.setAvailableQuantity(resource.getAvailableQuantity() + allocation.get().getAllocatedQuantity());
                resourceRepository.save(resource);
            }
            resourceAllocationRepository.deleteById(allocationId);
        } catch (Exception e) {
            logger.severe("Error deleting resource allocation: " + e.getMessage());
            throw new RuntimeException("Error deleting resource allocation", e);
        }
    }

    @Override
    public List<ResourceAllocationEntity> getResourceAllocationByUserId(Long userId) {
        logger.info("Fetching tasks assigned to user with ID: " + userId);
        return resourceAllocationRepository.findByUserId(userId);
    }
}
