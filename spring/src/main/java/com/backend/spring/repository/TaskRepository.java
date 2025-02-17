package com.backend.spring.repository;

import com.backend.spring.models.TaskEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    List<TaskEntity> findByAssignedToId(Long userId);

    @Query("SELECT t FROM TaskEntity t WHERE t.assignedTo.id = :userId")
    List<TaskEntity> getTasksByUserId(@Param("userId") Long userId);
}
