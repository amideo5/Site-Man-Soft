package com.backend.spring.service;

import com.backend.spring.models.TaskEntity;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskEntity createTask(TaskEntity task);
    TaskEntity updateTask(Long taskId, TaskEntity updatedTask);
    List<TaskEntity> getAllTasks();
    Optional<TaskEntity> getTaskById(Long taskId);
    void deleteTask(Long taskId);
    List<TaskEntity> getTasksByUserId(Long userId);
}
