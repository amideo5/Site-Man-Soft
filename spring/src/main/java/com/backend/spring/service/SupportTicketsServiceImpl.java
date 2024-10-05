package com.backend.spring.service;

import com.backend.spring.exceptions.SupportTicketAlreadyExistException;
import com.backend.spring.exceptions.SupportTicketNotFoundException;
import com.backend.spring.models.SupportTicketsEntity;
import com.backend.spring.repository.SupportTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketsServiceImpl implements SupportTicketsService{

    @Autowired
    private final SupportTicketRepository supportTicketRepository;

    public SupportTicketsServiceImpl(SupportTicketRepository supportTicketRepository) {
        this.supportTicketRepository = supportTicketRepository;
    }

    @Override
    public List<SupportTicketsEntity> getSupportTickets() {
        return supportTicketRepository.findAll();
    }

    @Override
    public Optional<SupportTicketsEntity> getSupportTicketById(Long id) throws SupportTicketNotFoundException {
        return supportTicketRepository.findById(id);
    }

    @Override
    public String updateSupportTicketById(Long id, SupportTicketsEntity supportTicket) throws SupportTicketNotFoundException {
        Optional<SupportTicketsEntity> supportTicketFromDB = supportTicketRepository.findById(id);

        if(supportTicketFromDB.isPresent()){
            SupportTicketsEntity oldSupportTicket = supportTicketFromDB.get();

            oldSupportTicket.setUser(supportTicket.getUser());
            oldSupportTicket.setProject(supportTicket.getProject());
            oldSupportTicket.setDescription(supportTicket.getDescription());
            oldSupportTicket.setStatus(supportTicket.getStatus());

            if (supportTicket.getResolvedAt() != null) {
                oldSupportTicket.setResolvedAt(LocalDateTime.now());
            }

            supportTicketRepository.save(oldSupportTicket);
            return "Support Ticket Updated";
        }
        else {
            throw new SupportTicketNotFoundException(id);
        }
    }

    @Override
    public String createSupportTicket(SupportTicketsEntity supportTicket) {
        SupportTicketsEntity newSupportTicketEntity = new SupportTicketsEntity();

        newSupportTicketEntity.setUser(supportTicket.getUser());
        newSupportTicketEntity.setProject(supportTicket.getProject());
        newSupportTicketEntity.setDescription(supportTicket.getDescription());
        newSupportTicketEntity.setStatus(supportTicket.getStatus());

        supportTicketRepository.save(supportTicket);
        return "Support Ticket Created";
    }

}
