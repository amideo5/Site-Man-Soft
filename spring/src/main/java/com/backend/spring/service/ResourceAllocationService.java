package com.backend.spring.service;

import com.backend.spring.models.ResourceAllocationEntity;
import com.backend.spring.models.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface ResourceAllocationService {

    /**
     * Creates a new resource allocation.
     *
     * @param resourceAllocation the resource allocation entity to be created
     * @return the created resource allocation entity
     */
    ResourceAllocationEntity createResourceAllocation(ResourceAllocationEntity resourceAllocation);

    /**
     * Updates an existing resource allocation.
     *
     * @param allocationId the ID of the resource allocation to be updated
     * @param updatedResourceAllocation the resource allocation entity with updated data
     * @return the updated resource allocation entity
     */
    ResourceAllocationEntity updateResourceAllocation(Long allocationId, ResourceAllocationEntity updatedResourceAllocation);

    /**
     * Retrieves all resource allocations.
     *
     * @return a list of all resource allocation entities
     */
    List<ResourceAllocationEntity> getAllResourceAllocations();

    /**
     * Retrieves a resource allocation by its ID.
     *
     * @param allocationId the ID of the resource allocation
     * @return an Optional containing the resource allocation entity if found, otherwise empty
     */
    Optional<ResourceAllocationEntity> getResourceAllocationById(Long allocationId);

    /**
     * Deletes a resource allocation by its ID.
     *
     * @param allocationId the ID of the resource allocation to be deleted
     */
    void deleteResourceAllocation(Long allocationId);

    List<ResourceAllocationEntity> getResourceAllocationByUserId(Long userId);
}
