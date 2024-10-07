package com.backend.spring.repository;

import com.backend.spring.models.ResourceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends CrudRepository<ResourceEntity, Long> {
}
