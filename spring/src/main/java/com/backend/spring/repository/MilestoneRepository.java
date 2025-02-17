package com.backend.spring.repository;

import com.backend.spring.models.MilestoneEntity;
import org.springframework.data.repository.CrudRepository;

public interface MilestoneRepository extends CrudRepository<MilestoneEntity, Long> {
}
