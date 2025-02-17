package com.backend.spring.repository;

import com.backend.spring.models.ResourceAllocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceAllocationRepository extends CrudRepository<ResourceAllocationEntity, Long> {

    List<ResourceAllocationEntity> findByUserId(Long userId);
}
