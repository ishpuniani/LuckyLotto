package com.poppulo.dao;

import com.poppulo.entity.Ticket;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketDaoImpl implements TicketDao {

    public TicketDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private NamedParameterJdbcTemplate template;

    public List<Ticket> getTickets() {
        return null;
    }

    public Ticket get(UUID id) {
        return null;
    }

    public Ticket check(UUID id) {
        return null;
    }

    public Ticket create(Ticket ticketDTO) {
        String query = "insert into tickets";
        ticketDTO.setId(UUID.randomUUID());
        ticketDTO.setCreatedAt(new Date(System.currentTimeMillis()));
        ticketDTO.setUpdatedAt(new Date(System.currentTimeMillis()));


        return ticketDTO;
    }

    public Ticket update(Ticket ticketDTO) {
        return null;
    }
}
