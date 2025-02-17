package com.backend.spring.repository;

import com.backend.spring.models.ProjectEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAll();

    ProjectEntity save(ProjectEntity projectEntity);

}
