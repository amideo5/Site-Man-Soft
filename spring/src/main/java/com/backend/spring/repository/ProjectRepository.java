package com.backend.spring.repository;

import com.backend.spring.models.ProjectEntity;
import com.backend.spring.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAll();

    ProjectEntity findByProjectName(String projectName);

    ProjectEntity save(ProjectEntity projectEntity);

}