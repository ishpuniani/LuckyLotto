package com.poppulo.dao;

import com.poppulo.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDao {

    public List<Ticket> getTickets();

    public Ticket get(UUID id);

    public Ticket create(Ticket ticket);

    public Ticket update(Ticket ticket);
}
