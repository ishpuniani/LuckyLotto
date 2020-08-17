package com.poppulo.mapper;

import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import com.poppulo.utils.LineUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.rowset.serial.SerialArray;
import javax.sql.rowset.serial.SerialException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LineInTicketRowMapper implements RowMapper<LineInTicket> {
    @Override
    public LineInTicket mapRow(ResultSet rs, int rowNum) throws SQLException {
        LineInTicket line = new LineInTicket(
                rs.getString("line_id"),
                (UUID) rs.getObject("ticket_id")
        );
        return line;
    }
}
