package com.poppulo.mapper;

import com.poppulo.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(UUID.fromString(rs.getString("id")));
        ticket.setChecked(rs.getBoolean("checked"));
        ticket.setTotalScore(rs.getFloat("total_score"));
        ticket.setCreatedAt(rs.getDate("created_at"));
        ticket.setUpdatedAt(rs.getDate("updated_at"));

        return ticket;
    }
}
