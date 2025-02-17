package com.backend.spring.service;

import com.backend.spring.models.ResourceEntity;

import java.util.List;
import java.util.Optional;

public interface ResourceService {

    /**
     * Creates a new resource.
     *
     * @param resource the resource to be created
     * @return the created resource entity
     */
    ResourceEntity createResource(ResourceEntity resource);

    /**
     * Updates an existing resource.
     *
     * @param resourceId the ID of the resource to be updated
     * @param updatedResource the resource entity with updated data
     * @return the updated resource entity
     */
    ResourceEntity updateResource(Long resourceId, ResourceEntity updatedResource);

    /**
     * Retrieves all resources.
     *
     * @return a list of all resource entities
     */
    List<ResourceEntity> getAllResources();

    /**
     * Retrieves a resource by its ID.
     *
     * @param resourceId the ID of the resource
     * @return an Optional containing the resource entity if found, otherwise empty
     */
    Optional<ResourceEntity> getResourceById(Long resourceId);

    /**
     * Deletes a resource by its ID.
     *
     * @param resourceId the ID of the resource to be deleted
     */
    void deleteResource(Long resourceId);
}
