package com.backend.spring.service;

import com.backend.spring.models.TimeEntryEntity;

public interface TimeEntryService {

    TimeEntryEntity clockIn(Long userId, Double latitude, Double longitude);

    TimeEntryEntity clockOut(Long userId);
}