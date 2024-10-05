package com.backend.spring.service;

import com.backend.spring.exceptions.SupportTicketAlreadyExistException;
import com.backend.spring.exceptions.SupportTicketNotFoundException;
import com.backend.spring.models.SupportTicketsEntity;

import java.util.List;
import java.util.Optional;

public interface SupportTicketsService {

    public List<SupportTicketsEntity> getSupportTickets();

    public Optional<SupportTicketsEntity> getSupportTicketById(Long Id) throws SupportTicketNotFoundException;

    public String updateSupportTicketById(Long id, SupportTicketsEntity supportTicket) throws SupportTicketNotFoundException;

    public String createSupportTicket(SupportTicketsEntity supportTicket);
}
