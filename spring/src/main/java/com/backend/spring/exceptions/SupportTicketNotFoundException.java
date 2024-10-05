package com.backend.spring.exceptions;

public class SupportTicketNotFoundException extends Exception{

    public SupportTicketNotFoundException(Long id) {
        super("Support Ticket with id : " + id + "NOT FOUND");
    }

}
