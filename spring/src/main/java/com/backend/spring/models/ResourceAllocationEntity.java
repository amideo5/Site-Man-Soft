package com.backend.spring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

/**
 * Represents the allocation of a resource to a user.
 */
@Getter
@Setter
@Entity
@Table(name = "resource_allocations")
public class ResourceAllocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    private ResourceEntity resource;

    @Formula("(SELECT r.name FROM resources r WHERE r.id = resource_id)")
    private String resourceName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer allocatedQuantity;

    @Column(name = "allocated_at", updatable = false)
    private LocalDateTime allocatedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "return_date", nullable = true)
    private LocalDateTime returnDate;

    @PrePersist
    protected void onCreate() {
        allocatedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

