package com.backend.spring.repository;

import com.backend.spring.models.TenantEntity;
import org.springframework.data.repository.CrudRepository;

public interface TenantRepository extends CrudRepository<TenantEntity, Long> {
}
