package com.backend.spring.service;

import com.backend.spring.models.MilestoneEntity;

import java.util.List;
import java.util.Optional;

public interface MilestoneService {
    MilestoneEntity createMilestone(MilestoneEntity milestone);
    MilestoneEntity updateMilestone(Long milestoneId, MilestoneEntity updatedMilestone);
    List<MilestoneEntity> getAllMilestones();
    Optional<MilestoneEntity> getMilestoneById(Long milestoneId);
    void deleteMilestone(Long milestoneId);
}
