package com.backend.spring.controller;

import com.backend.spring.models.SupportTicketsEntity;
import com.backend.spring.service.SupportTicketsService;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/support-tickets")
public class SupportTicketController {

    private final SupportTicketsService supportTicketsService;

    @Autowired
    public SupportTicketController(SupportTicketsService supportTicketsService) {
        this.supportTicketsService = supportTicketsService;
    }

    @PostMapping
    public ResponseEntity<SupportTicketsEntity> createSupportTicket(@RequestBody SupportTicketsEntity supportTicketEntity, HttpServletRequest request, HttpServletResponse response) {
        SupportTicketsEntity createdTicket = supportTicketsService.createSupportTicket(supportTicketEntity);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<SupportTicketsEntity> updateSupportTicket(@PathVariable Long ticketId, @RequestBody SupportTicketsEntity updatedSupportTicket, HttpServletRequest request, HttpServletResponse response) {
        SupportTicketsEntity updatedTicketEntity = supportTicketsService.updateSupportTicket(ticketId, updatedSupportTicket);
        return new ResponseEntity<>(updatedTicketEntity, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<SupportTicketsEntity> getSupportTicketById(@PathVariable Long ticketId, HttpServletRequest request, HttpServletResponse response) {
        Optional<SupportTicketsEntity> ticket = supportTicketsService.getSupportTicketById(ticketId);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<SupportTicketsEntity>> getAllSupportTickets(HttpServletRequest request, HttpServletResponse response) {
        List<SupportTicketsEntity> tickets = supportTicketsService.getAllSupportTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteSupportTicket(@PathVariable Long ticketId, HttpServletRequest request, HttpServletResponse response) {
        supportTicketsService.deleteSupportTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
