package com.poppulo.mapper;

import com.poppulo.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        SqlRowSet sqlRowSet = new ResultSetWrappingSqlRowSet(rs);
        return mapRowSet(sqlRowSet, rowNum);
    }

    public Ticket mapRowSet(SqlRowSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(UUID.fromString(rs.getString("id")));
        ticket.setChecked(rs.getBoolean("checked"));
        ticket.setTotalScore(rs.getFloat("total_score"));
        ticket.setCreatedAt(rs.getTimestamp("created_at"));
        ticket.setUpdatedAt(rs.getTimestamp("updated_at"));
        return ticket;
    }
}
