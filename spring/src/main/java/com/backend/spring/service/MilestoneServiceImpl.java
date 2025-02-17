package com.backend.spring.service;

import com.backend.spring.models.MilestoneEntity;
import com.backend.spring.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MilestoneServiceImpl implements MilestoneService {

    private static final Logger logger = Logger.getLogger(MilestoneServiceImpl.class.getName());

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Override
    @Transactional
    public MilestoneEntity createMilestone(MilestoneEntity milestone) {
        logger.info("Creating milestone: " + milestone.getName());
        try {
            return milestoneRepository.save(milestone);
        } catch (Exception e) {
            logger.severe("Error creating milestone: " + e.getMessage());
            throw new RuntimeException("Error creating milestone", e);
        }
    }

    @Override
    @Transactional
    public MilestoneEntity updateMilestone(Long milestoneId, MilestoneEntity updatedMilestone) {
        logger.info("Updating milestone with ID: " + milestoneId);
        Optional<MilestoneEntity> existingMilestone = milestoneRepository.findById(milestoneId);
        if (existingMilestone.isPresent()) {
            MilestoneEntity milestone = existingMilestone.get();
            milestone.setName(updatedMilestone.getName());
            milestone.setDueDate(updatedMilestone.getDueDate());
            milestone.setProject(updatedMilestone.getProject());
            try {
                return milestoneRepository.save(milestone);
            } catch (Exception e) {
                logger.severe("Error updating milestone: " + e.getMessage());
                throw new RuntimeException("Error updating milestone", e);
            }
        } else {
            logger.warning("Milestone not found with ID: " + milestoneId);
            throw new RuntimeException("Milestone not found");
        }
    }

    @Override
    public List<MilestoneEntity> getAllMilestones() {
        logger.info("Fetching all milestones");
        return (List<MilestoneEntity>) milestoneRepository.findAll();
    }

    @Override
    public Optional<MilestoneEntity> getMilestoneById(Long milestoneId) {
        logger.info("Fetching milestone with ID: " + milestoneId);
        return milestoneRepository.findById(milestoneId);
    }

    @Override
    @Transactional
    public void deleteMilestone(Long milestoneId) {
        logger.info("Deleting milestone with ID: " + milestoneId);
        try {
            milestoneRepository.deleteById(milestoneId);
        } catch (Exception e) {
            logger.severe("Error deleting milestone: " + e.getMessage());
            throw new RuntimeException("Error deleting milestone", e);
        }
    }
}
