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

        return resourceRepository.findAll();

    }

    @Override
    public Optional<ResourceEntity> getResourceById(Long id) throws ResourceNotFoundException {
       return resourceRepository.findById(id);
    }

    @Override
    public Optional<ResourceEntity> getResourceByResourceName(String resourceName) throws ResourceNotFoundException {
        return resourceRepository.findByResourceName(resourceName);
    }

    @Override
    public String createResource(ResourceEntity resource) {

        ResourceEntity newResource = new ResourceEntity();

        newResource.setResourceName(resource.getResourceName());
        newResource.setQuantity(resource.getQuantity());
        newResource.setAvailable_quantity(resource.getAvailable_quantity());
        newResource.setAllocated_to(resource.getAllocated_to());

        resourceRepository.save(newResource);
        return "Resource Created";
    }

    @Override
    public String updateResource(Long id, ResourceEntity resource) throws ResourceNotFoundException {
        Optional<ResourceEntity> resourceFromDB = resourceRepository.findById(id);

        if(resourceFromDB.isPresent()){
            ResourceEntity oldResource  = resourceFromDB.get();

            oldResource.setResourceName(resource.getResourceName());
            oldResource.setQuantity(resource.getQuantity());
            oldResource.setAvailable_quantity(resource.getAvailable_quantity());
            oldResource.setAllocated_to(resource.getAllocated_to());

            resourceRepository.save(oldResource);
            return "Resource Updated Successfully";
        }
        else {
            throw new ResourceNotFoundException(id);
        }
    }
}
