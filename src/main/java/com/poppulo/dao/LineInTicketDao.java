package com.poppulo.dao;

import com.poppulo.entity.LineInTicket;

import java.util.List;
import java.util.UUID;

public interface LineInTicketDao {
    public void save(List<LineInTicket> lineInTickets);
}
