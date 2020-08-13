package com.poppulo.dao;

import com.poppulo.entity.LineInTicket;

import java.util.UUID;

public interface LineInTicketDao {
    public LineInTicket create(LineInTicket lineInTicketDTO);

    public LineInTicket get(UUID id);

    public LineInTicket getByTicket(UUID ticketId);
}
