package com.backend.spring.repository;

import com.backend.spring.models.SupportTicketsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends CrudRepository<SupportTicketsEntity, Long> {

    List<SupportTicketsEntity> findAll();

}
