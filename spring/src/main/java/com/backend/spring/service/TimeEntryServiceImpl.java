package com.backend.spring.service;

import com.backend.spring.models.TimeEntryEntity;
import com.backend.spring.models.UserEntity;
import com.backend.spring.repository.TimeEntryRepository;
import com.backend.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TimeEntryServiceImpl implements TimeEntryService{

    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;

    public TimeEntryServiceImpl(TimeEntryRepository timeEntryRepository, UserRepository userRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public TimeEntryEntity clockIn(Long userId, Double latitude, Double longitude) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        Optional<TimeEntryEntity> existingEntry = timeEntryRepository.findByUserAndClockInDate(user, today);
        if (existingEntry.isPresent()) {
            throw new RuntimeException("User has already clocked in today");
        }

        TimeEntryEntity timeEntry = new TimeEntryEntity(user, LocalDateTime.now(), today, latitude, longitude);
        return timeEntryRepository.save(timeEntry);
    }

    @Override
    public TimeEntryEntity clockOut(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userOptional.get();
        LocalDate today = LocalDate.now();

        // Find today's clock-in entry
        Optional<TimeEntryEntity> existingEntry = timeEntryRepository.findByUserAndClockInDate(user, today);
        if (existingEntry.isEmpty()) {
            throw new RuntimeException("User has not clocked in today");
        }

        TimeEntryEntity timeEntry = existingEntry.get();

        // Ensure the user hasn't already clocked out
        if (timeEntry.getClockOutTime() != null) {
            throw new RuntimeException("User has already clocked out today");
        }

        // Update the clock-out time
        timeEntry.setClockOutTime(LocalDateTime.now());
        return timeEntryRepository.save(timeEntry);
    }
}
