package com.backend.spring.controller;

import com.backend.spring.models.TaskEntity;
import com.backend.spring.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity taskEntity, HttpServletRequest request, HttpServletResponse response) {
        TaskEntity createdTask = taskService.createTask(taskEntity);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable Long taskId, @RequestBody TaskEntity updatedTask, HttpServletRequest request, HttpServletResponse response) {
        TaskEntity updatedTaskEntity = taskService.updateTask(taskId, updatedTask);
        return new ResponseEntity<>(updatedTaskEntity, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long taskId, HttpServletRequest request, HttpServletResponse response) {
        Optional<TaskEntity> task = taskService.getTaskById(taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getAllTasks(HttpServletRequest request, HttpServletResponse response) {
        List<TaskEntity> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, HttpServletRequest request, HttpServletResponse response) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskEntity>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskEntity> tasks = taskService.getTasksByUserId(userId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
