package com.backend.spring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "resources")
public class ResourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String resourceName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer available_quantity;

    @ManyToOne
    @JoinColumn(name = "allocated_to_user", referencedColumnName = "id", nullable = true)
    private UserEntity allocated_to;

}
