package com.backend.spring.controller;


import com.backend.spring.exceptions.SupportTicketAlreadyExistException;
import com.backend.spring.exceptions.SupportTicketNotFoundException;
import com.backend.spring.models.SupportTicketsEntity;
import com.backend.spring.service.SupportTicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/support-tickets")
@CrossOrigin(origins = "*")
public class SupportTicketController {

    @Autowired
    private SupportTicketsService supportTicketsService;

    @GetMapping("/getSupportTickets")
    public ResponseEntity<?> getSupportTickets(){
        List<SupportTicketsEntity> supportTickets = supportTicketsService.getSupportTickets();
        return ResponseEntity.status(HttpStatus.OK).body(supportTickets);
    }

    @GetMapping("/getSupportTicketById/{id}")
    public ResponseEntity<?> getSupportTicketById(@PathVariable Long id) throws SupportTicketNotFoundException{
        try{
            Optional<SupportTicketsEntity> supportTicket = supportTicketsService.getSupportTicketById(id);
            return ResponseEntity.status(HttpStatus.OK).body(supportTicket);
        }
        catch(SupportTicketNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/createSupportTicket")
    public ResponseEntity<?> createSupportTicket(@RequestBody SupportTicketsEntity supportTicket){
        try{
            String createdSupportTicketResult = supportTicketsService.createSupportTicket(supportTicket);
            return ResponseEntity.status(HttpStatus.OK).body(createdSupportTicketResult);
        }
        catch(SupportTicketAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/updateSupportTicket/{id}")
    public ResponseEntity<?> updateSupportTicketById(@PathVariable Long id, @RequestBody SupportTicketsEntity supportTicket){
        try{
            String updatedSupportTicketResult = supportTicketsService.updateSupportTicketById(id, supportTicket);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSupportTicketResult);
        }
        catch(SupportTicketNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
