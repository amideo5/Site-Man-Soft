package com.backend.spring.service;

import com.backend.spring.models.SupportTicketsEntity;

import java.util.List;
import java.util.Optional;

public interface SupportTicketsService {

    /**
     * Creates a new support ticket.
     *
     * @param supportTicket the support ticket entity to be created
     * @return the created support ticket entity
     */
    SupportTicketsEntity createSupportTicket(SupportTicketsEntity supportTicket);

    /**
     * Updates an existing support ticket.
     *
     * @param ticketId the ID of the support ticket to be updated
     * @param updatedSupportTicket the support ticket entity with updated data
     * @return the updated support ticket entity
     */
    SupportTicketsEntity updateSupportTicket(Long ticketId, SupportTicketsEntity updatedSupportTicket);

    /**
     * Retrieves all support tickets.
     *
     * @return a list of all support ticket entities
     */
    List<SupportTicketsEntity> getAllSupportTickets();

    /**
     * Retrieves a support ticket by its ID.
     *
     * @param ticketId the ID of the support ticket
     * @return an Optional containing the support ticket entity if found, otherwise empty
     */
    Optional<SupportTicketsEntity> getSupportTicketById(Long ticketId);

    /**
     * Deletes a support ticket by its ID.
     *
     * @param ticketId the ID of the support ticket to be deleted
     */
    void deleteSupportTicket(Long ticketId);
}
