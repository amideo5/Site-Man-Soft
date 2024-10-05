package com.backend.spring.repository;

import com.backend.spring.models.SupportTicketsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupportTicketRepository extends CrudRepository<SupportTicketsEntity, Long> {

    List<SupportTicketsEntity> findAll();

}
