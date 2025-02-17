package com.backend.spring.controller;

import com.backend.spring.models.MilestoneEntity;
import com.backend.spring.service.MilestoneService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @Autowired
    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    public ResponseEntity<MilestoneEntity> createMilestone(@RequestBody MilestoneEntity milestoneEntity, HttpServletRequest request, HttpServletResponse response) {
        MilestoneEntity createdMilestone = milestoneService.createMilestone(milestoneEntity);
        return new ResponseEntity<>(createdMilestone, HttpStatus.CREATED);
    }

    @PutMapping("/{milestoneId}")
    public ResponseEntity<MilestoneEntity> updateMilestone(@PathVariable Long milestoneId, @RequestBody MilestoneEntity updatedMilestone, HttpServletRequest request, HttpServletResponse response) {
        MilestoneEntity updatedMilestoneEntity = milestoneService.updateMilestone(milestoneId, updatedMilestone);
        return new ResponseEntity<>(updatedMilestoneEntity, HttpStatus.OK);
    }

    @GetMapping("/{milestoneId}")
    public ResponseEntity<MilestoneEntity> getMilestoneById(@PathVariable Long milestoneId, HttpServletRequest request, HttpServletResponse response) {
        Optional<MilestoneEntity> milestone = milestoneService.getMilestoneById(milestoneId);
        return milestone.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<MilestoneEntity>> getAllMilestones(HttpServletRequest request, HttpServletResponse response) {
        List<MilestoneEntity> milestones = milestoneService.getAllMilestones();
        return new ResponseEntity<>(milestones, HttpStatus.OK);
    }

    @DeleteMapping("/{milestoneId}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long milestoneId, HttpServletRequest request, HttpServletResponse response) {
        milestoneService.deleteMilestone(milestoneId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
