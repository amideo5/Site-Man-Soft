package com.backend.spring.repository;

import com.backend.spring.models.ProjectUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends CrudRepository<ProjectUserEntity, Long> {

    List<ProjectUserEntity> findAll();

//    ProjectUserEntity findByProjectUserName(String projectUserName);

    ProjectUserEntity save(ProjectUserEntity projectUserEntity);

    List<ProjectUserEntity> findByProjectId(Long projectId);

    List<ProjectUserEntity> findByUserId(Long userId);

}
