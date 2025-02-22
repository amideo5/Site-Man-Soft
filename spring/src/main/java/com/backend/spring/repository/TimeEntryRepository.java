package com.backend.spring.repository;

import com.backend.spring.models.TimeEntryEntity;
import com.backend.spring.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeEntryRepository extends CrudRepository<TimeEntryEntity, Long> {
    Optional<TimeEntryEntity> findByUserAndClockInDate(UserEntity user, LocalDate clockInDate);

    List<TimeEntryEntity> findByUserId(Long userId);
}


