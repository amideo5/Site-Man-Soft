package com.backend.spring.service;

import com.backend.spring.models.SupportTicketsEntity;
import com.backend.spring.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketsServiceImpl implements SupportTicketsService {

    private final SupportTicketRepository supportTicketsRepository;

    @Autowired
    public SupportTicketsServiceImpl(SupportTicketRepository supportTicketsRepository) {
        this.supportTicketsRepository = supportTicketsRepository;
    }

    @Override
    public SupportTicketsEntity createSupportTicket(SupportTicketsEntity supportTicket) {
        return supportTicketsRepository.save(supportTicket);
    }

    @Override
    public SupportTicketsEntity updateSupportTicket(Long ticketId, SupportTicketsEntity updatedSupportTicket) {
        Optional<SupportTicketsEntity> existingTicketOptional = supportTicketsRepository.findById(ticketId);
        if (existingTicketOptional.isPresent()) {
            SupportTicketsEntity existingTicket = existingTicketOptional.get();
            existingTicket.setDescription(updatedSupportTicket.getDescription());
            existingTicket.setStatus(updatedSupportTicket.getStatus());
            existingTicket.setResolvedAt(updatedSupportTicket.getResolvedAt());
            return supportTicketsRepository.save(existingTicket);
        } else {
            // Handle the case when the ticket doesn't exist
            throw new RuntimeException("Support ticket not found for id: " + ticketId);
        }
    }

    @Override
    public List<SupportTicketsEntity> getAllSupportTickets() {
        return supportTicketsRepository.findAll();
    }

    @Override
    public Optional<SupportTicketsEntity> getSupportTicketById(Long ticketId) {
        return supportTicketsRepository.findById(ticketId);
    }

    @Override
    public void deleteSupportTicket(Long ticketId) {
        supportTicketsRepository.deleteById(ticketId);
    }
}
