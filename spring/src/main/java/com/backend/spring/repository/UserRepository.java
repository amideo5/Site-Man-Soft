package com.backend.spring.repository;

import com.backend.spring.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    List<UserEntity> findAll();

    UserEntity save(UserEntity userEntity);

}
