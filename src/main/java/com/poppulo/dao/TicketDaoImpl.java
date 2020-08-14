package com.poppulo.dao;

import com.poppulo.entity.Ticket;
import com.poppulo.exception.TicketException;
import com.poppulo.mapper.TicketRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class TicketDaoImpl implements TicketDao {

    public TicketDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private NamedParameterJdbcTemplate template;

    /**
     * Get all tickets in the database
     *
     * @return list of ticket objects
     */
    public List<Ticket> getTickets() {
        String query = "select * from tickets";
        return template.query(query, new TicketRowMapper());
        //TODO: Efficient query, use joins and custom mapper
    }

    /**
     * Get ticket by ID.
     *
     * @param ticketId uuid of the ticket
     * @return ticket object by id
     */
    public Ticket get(UUID ticketId) {
        String query = "select * from tickets where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", ticketId);

        List<Ticket> tickets = template.query(query, param, new TicketRowMapper());
        if (tickets.size() == 0) {
            throw new TicketException("Unable to find ticket: " + ticketId);
        }

        return tickets.get(0);
    }

    /**
     * Create the ticket row in database
     *
     * @param ticket ticket to be created.
     * @return saved ticket in the database.
     */
    public Ticket create(Ticket ticket) {
        String query = "insert into tickets(id, checked, total_score, created_at, updated_at) " +
                "values(:id,:checked, :totalScore, :createdAt,:updatedAt)";

        ticket.setId(UUID.randomUUID());
        ticket.setCreatedAt(new Date(System.currentTimeMillis()));
        ticket.setUpdatedAt(new Date(System.currentTimeMillis()));

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("checked", ticket.isChecked())
                .addValue("totalScore", ticket.getTotalScore())
                .addValue("createdAt", ticket.getCreatedAt())
                .addValue("updatedAt", ticket.getUpdatedAt());

        int createdRows = template.update(query, param, holder);
        if (createdRows == 0) {
            throw new TicketException("Unable to create ticket: " + ticket);
        }

        return ticket;
    }

    /**
     * Function to update ticket object in database
     *
     * @param ticket
     * @return
     */
    public Ticket update(Ticket ticket) {
        String query = "update tickets set checked=:checked, total_score=:totalScore, updated_at=:updatedAt where id=:id";
        ticket.setUpdatedAt(new Date(System.currentTimeMillis()));

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("checked", ticket.isChecked())
                .addValue("totalScore", ticket.getTotalScore())
                .addValue("updatedAt", ticket.getUpdatedAt())
                .addValue("id", ticket.getId());

        int updateCount = template.update(query, param, holder);
        if (updateCount != 1) {
            throw new TicketException("Unable to update ticket: " + ticket);
        }
        return ticket;
    }
}
