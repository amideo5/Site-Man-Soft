package com.backend.spring.repository;

import com.backend.spring.models.ResourceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends CrudRepository<ResourceEntity, Long> {

    List<ResourceEntity> findAll();



}
