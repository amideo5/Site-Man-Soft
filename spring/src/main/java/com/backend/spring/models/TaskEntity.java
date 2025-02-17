package com.backend.spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a task within a milestone.
 */
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "milestone_id", referencedColumnName = "id", nullable = false)
    private MilestoneEntity milestone;

    @ManyToOne
    @JoinColumn(name = "assigned_to_user_id", referencedColumnName = "id", nullable = true)
    private UserEntity assignedTo;

    @Column(nullable = false)
    private Boolean isComplete;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
