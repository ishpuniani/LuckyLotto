package com.poppulo.dao;

import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import com.poppulo.exception.TicketException;
import com.poppulo.mapper.LineInTicketRowMapper;
import com.poppulo.mapper.LineRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class LineInTicketDaoImpl implements LineInTicketDao{

    private final Logger logger = LoggerFactory.getLogger(LineInTicketDaoImpl.class);

    public LineInTicketDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private NamedParameterJdbcTemplate template;

    /**
     * Function to get all lines_in_tickets from DB. Used in tests.
     * @return list of lines in DB
     */
    @Override
    public List<LineInTicket> getAll() {
        String query = "select * from lines_in_tickets";

        SqlParameterSource param = new MapSqlParameterSource();
        List<LineInTicket> lines = template.query(query, param, new LineInTicketRowMapper());

        logger.info("Retrieved " + lines.size() + " lines in tickets");
        return lines;
    }

    /**
     * Function to save ticket-line mapping in the database.
     *
     * @param lineInTickets the object to be saved.
     */
    @Override
    public void save(List<LineInTicket> lineInTickets) {
        if(lineInTickets == null || lineInTickets.size() == 0) {
            throw new TicketException("No lineInTicket to save");
        }

        String query = "insert into lines_in_tickets(id ,line_id, ticket_id) " +
                "values(:id, :lineId, :ticketId) " +
                "ON CONFLICT(id) DO NOTHING";

        SqlParameterSource[] parameterSources = new SqlParameterSource[lineInTickets.size()];

        int counter = 0;
        for(LineInTicket lineInTicket : lineInTickets) {
            if(lineInTicket.getId() == null) {
                lineInTicket.setId(UUID.randomUUID());
            }
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("id", lineInTicket.getId())
                    .addValue("lineId", lineInTicket.getLineId())
                    .addValue("ticketId", lineInTicket.getTicketId());

            parameterSources[counter++] = param;
        }

        int[] createdRows = template.batchUpdate(query, parameterSources);
    }
}
