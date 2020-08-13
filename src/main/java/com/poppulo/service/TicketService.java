package com.poppulo.service;

import com.poppulo.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    public List<Ticket> getTickets();

    public Ticket get(UUID id);

    public Ticket create(int lineCount);

    public Ticket amend(UUID ticketId, int lineCount);

    public Ticket checkStatus(UUID id);

}
