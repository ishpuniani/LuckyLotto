package com.poppulo.dao;

import com.poppulo.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDao {

    public List<Ticket> getTickets();

    public Ticket get(UUID id);

    public Ticket check(UUID id);

    public Ticket create(Ticket ticketDTO);

    public Ticket update(Ticket ticketDTO);
}
