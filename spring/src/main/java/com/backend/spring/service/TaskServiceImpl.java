package com.backend.spring.service;

import com.backend.spring.models.TaskEntity;
import com.backend.spring.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = Logger.getLogger(TaskServiceImpl.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskEntity createTask(TaskEntity task) {
        logger.info("Creating task: " + task.getDescription());
        try {
            return taskRepository.save(task);
        } catch (Exception e) {
            logger.severe("Error creating task: " + e.getMessage());
            throw new RuntimeException("Error creating task", e);
        }
    }

    @Override
    @Transactional
    public TaskEntity updateTask(Long taskId, TaskEntity updatedTask) {
        logger.info("Updating task with ID: " + taskId);
        Optional<TaskEntity> existingTask = taskRepository.findById(taskId);
        if (existingTask.isPresent()) {
            TaskEntity task = existingTask.get();
            task.setDescription(updatedTask.getDescription());
            task.setAssignedTo(updatedTask.getAssignedTo());
            task.setIsComplete(updatedTask.getIsComplete());
            try {
                return taskRepository.save(task);
            } catch (Exception e) {
                logger.severe("Error updating task: " + e.getMessage());
                throw new RuntimeException("Error updating task", e);
            }
        } else {
            logger.warning("Task not found with ID: " + taskId);
            throw new RuntimeException("Task not found");
        }
    }

    @Override
    public List<TaskEntity> getAllTasks() {
        logger.info("Fetching all tasks");
        return (List<TaskEntity>) taskRepository.findAll();
    }

    @Override
    public Optional<TaskEntity> getTaskById(Long taskId) {
        logger.info("Fetching task with ID: " + taskId);
        return taskRepository.findById(taskId);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        logger.info("Deleting task with ID: " + taskId);
        try {
            taskRepository.deleteById(taskId);
        } catch (Exception e) {
            logger.severe("Error deleting task: " + e.getMessage());
            throw new RuntimeException("Error deleting task", e);
        }
    }

    @Override
    public List<TaskEntity> getTasksByUserId(Long userId) {
        logger.info("Fetching tasks assigned to user with ID: " + userId);
        return taskRepository.findByAssignedToId(userId);
    }
}
