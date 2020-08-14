package com.poppulo.dao;

import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class LineInTicketDaoImpl implements LineInTicketDao{

    public LineInTicketDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private NamedParameterJdbcTemplate template;

    /**
     * Function to save ticket-line mapping in the database.
     *
     * @param lineInTickets the rows to be saved.
     */
    @Override
    public void save(List<LineInTicket> lineInTickets) {
        String query = "insert into lines_in_tickets(line_id, ticket_id) " +
                "values(:lineId, :ticketId) " +
                "ON CONFLICT(line_id, ticket_id) DO NOTHING";

        SqlParameterSource[] parameterSources = new SqlParameterSource[lineInTickets.size()];

        int counter = 0;
        for(LineInTicket lineInTicket : lineInTickets) {
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("lineId", lineInTicket.getLineId())
                    .addValue("ticketId", lineInTicket.getTicketId());

            parameterSources[counter++] = param;
        }

        int[] createdRows = template.batchUpdate(query, parameterSources);
    }
}
