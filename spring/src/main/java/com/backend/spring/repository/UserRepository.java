package com.backend.spring.repository;

import com.backend.spring.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> findByTenantId(Long tenantId);

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findByUsername(String username);

}
