package com.backend.spring.exceptions;

public class SupportTicketAlreadyExistException extends Exception{

    public SupportTicketAlreadyExistException(Long id) {

        super("Support Ticket with name : " + id + "already exists");

    }
}
