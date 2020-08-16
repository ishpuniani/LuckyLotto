package com.poppulo.dao;

import com.poppulo.entity.Line;
import com.poppulo.entity.Ticket;
import com.poppulo.exception.TicketException;
import com.poppulo.mapper.LineRowMapper;
import com.poppulo.mapper.TicketRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public List<Ticket> getAll() {
        String query = "select t.id, l.id as line_id, l.elements, l.score, t.checked, t.total_score, t.created_at, t.updated_at from tickets t " +
                "join lines_in_tickets lit on t.id = lit.ticket_id " +
                "join lines l on lit.line_id = l.id " +
                "order by t.id, l.score desc";

        SqlRowSet rowSet = template.queryForRowSet(query, new MapSqlParameterSource());
        List<Ticket> tickets = null;
        try {
            tickets = getTicketsFromRowSet(rowSet, false);
        } catch (SerialException serialException) {
            throw new TicketException(serialException.getMessage());
        }

        return tickets;
    }

    private List<Ticket> getTicketsFromRowSet(SqlRowSet rowSet, boolean withScore) throws SerialException {
        List<Ticket> tickets = new ArrayList<Ticket>();
        Map<UUID, List<Line>> lineMap = new LinkedHashMap<>();

        TicketRowMapper ticketRowMapper = new TicketRowMapper();
        LineRowMapper lineRowMapper = new LineRowMapper();

        int rowNum = 0;
        while (rowSet.next()) {
            Ticket ticket = ticketRowMapper.mapRowSet(rowSet, rowNum);
            if(!lineMap.containsKey(ticket.getId())) {
                tickets.add(ticket);
                lineMap.put(ticket.getId(), new ArrayList<Line>());
            }
            Line line = lineRowMapper.mapJoinRowSet(rowSet, rowNum);
            if(!withScore && !ticket.isChecked()) {
                line.setScore(null);
            }
            lineMap.get(ticket.getId()).add(line);
            rowNum++;
        }

        for(Ticket ticket: tickets) {
            ticket.setLines(lineMap.get(ticket.getId()));
        }

        return tickets;
    }

    /**
     * Get ticket by ID.
     * Query joins tables lines_in_tickets and lines to get line data in ticket.
     *
     * @param ticketId uuid of the ticket
     * @return ticket object by id
     */
    public Ticket get(UUID ticketId, boolean withScore) {
        String query = "select t.id, l.id as line_id, l.elements, l.score, t.checked, t.total_score, t.created_at, t.updated_at from tickets t " +
                "join lines_in_tickets lit on t.id = lit.ticket_id " +
                "join lines l on lit.line_id = l.id " +
                "where ticket_id=:id " +
                "order by l.score desc;";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", ticketId);

        SqlRowSet rowSet = template.queryForRowSet(query, param);
        List<Ticket> tickets = null;
        try {
            tickets = getTicketsFromRowSet(rowSet, withScore);
        } catch (SerialException serialException) {
            throw new TicketException(serialException.getMessage());
        }

        if (tickets.size() == 0) {
            throw new TicketException("Unable to find ticket: " + ticketId);
        }

        return tickets.get(0);
    }

    /**
     * Create the ticket row in database
     * Query joins lines_in_tickets table along with lines to get line data.
     *
     * @param ticket ticket to be created.
     * @return saved ticket in the database.
     */
    public Ticket save(Ticket ticket) {
        if(ticket == null) {
            throw new TicketException("No ticket to save");
        }

        String query = "insert into tickets(id, checked, total_score, created_at, updated_at) " +
                "values(:id,:checked, :totalScore, :createdAt,:updatedAt) " +
                "ON CONFLICT (id) do update set " +
                "checked=:checked, total_score=:totalScore, updated_at=:updatedAt";

        if(ticket.getId() == null) {
            //New object
            ticket.setId(UUID.randomUUID());
            ticket.setTotalScore(-1F);
            ticket.setCreatedAt(new Date(System.currentTimeMillis()));
        }
        ticket.setUpdatedAt(new Date(System.currentTimeMillis()));

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", ticket.getId())
                .addValue("checked", ticket.isChecked())
                .addValue("totalScore", ticket.getTotalScore())
                .addValue("createdAt", ticket.getCreatedAt())
                .addValue("updatedAt", ticket.getUpdatedAt());

        int changedRows = template.update(query, param, holder);
        if (changedRows == 0) {
            throw new TicketException("Unable to save ticket: " + ticket);
        }

        return ticket;
    }
}
