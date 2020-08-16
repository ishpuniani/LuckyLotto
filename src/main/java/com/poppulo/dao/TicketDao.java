package com.poppulo.dao;

import com.poppulo.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDao {

    public List<Ticket> getAll();

    public Ticket get(UUID id, boolean withScore);

    public Ticket save(Ticket ticket);
}
