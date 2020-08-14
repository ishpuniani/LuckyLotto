package com.poppulo.service;

import com.poppulo.entity.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("TicketService")
@Transactional
public interface TicketService {

    public List<Ticket> getTickets();

    public Ticket get(UUID id);

    public Ticket create(int lineCount);

    public Ticket amend(UUID ticketId, int lineCount);

    public Ticket checkStatus(UUID id);

}
