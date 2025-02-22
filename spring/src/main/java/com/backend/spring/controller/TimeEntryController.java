package com.backend.spring.controller;

import com.backend.spring.models.TimeEntryEntity;
import com.backend.spring.service.TimeEntryService;
import com.backend.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/time-tracker")
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    @Autowired
    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @PostMapping("/clock-in")
    public ResponseEntity<?> clockIn(@RequestBody Map<String, Object> payload) {
        try {
            Long userId = Long.valueOf(payload.get("userId").toString());
            Double latitude = payload.containsKey("latitude") ? (Double) payload.get("latitude") : null;
            Double longitude = payload.containsKey("longitude") ? (Double) payload.get("longitude") : null;

            TimeEntryEntity timeEntry = timeEntryService.clockIn(userId, latitude, longitude);
            return ResponseEntity.ok(timeEntry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/clock-out")
    public ResponseEntity<?> clockOut(@RequestBody Map<String, Object> payload) {
        try {
            Long userId = Long.valueOf(payload.get("userId").toString());

            TimeEntryEntity timeEntry = timeEntryService.clockOut(userId);
            return ResponseEntity.ok(timeEntry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public List<TimeEntryEntity> getTimeEntriesByUserId(@PathVariable Long userId) {
        return timeEntryService.getTimeEntriesByUserId(userId);
    }
}
