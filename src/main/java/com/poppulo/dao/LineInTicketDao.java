package com.poppulo.dao;

import com.poppulo.entity.LineInTicket;

import java.util.List;
import java.util.UUID;

public interface LineInTicketDao {
    public List<LineInTicket> getAll();

    public void save(List<LineInTicket> lineInTickets);
}
