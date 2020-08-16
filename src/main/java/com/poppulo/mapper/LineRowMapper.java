package com.poppulo.mapper;

import com.poppulo.entity.Line;
import com.poppulo.utils.LineUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.rowset.serial.SerialArray;
import javax.sql.rowset.serial.SerialException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LineRowMapper implements RowMapper<Line> {
    @Override
    public Line mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResultSetWrappingSqlRowSet rowSet = new ResultSetWrappingSqlRowSet(rs);
        return mapRowSet(rowSet, rowNum);
    }

    public Line mapRowSet(SqlRowSet rs, int rowNum) throws SerialException {
        Line line = mapJoinRowSet(rs, rowNum);
        line.setId(rs.getString("id"));
        line.setCreatedAt(rs.getTimestamp("created_at"));
        line.setUpdatedAt(rs.getTimestamp("updated_at"));
        return line;
    }

    public Line mapJoinRowSet(SqlRowSet rs, int rowNum) throws SerialException {
        Line line = new Line();
        Object[] elementArr = (Object[]) ((SerialArray)rs.getObject("elements")).getArray();
        line.setElements(LineUtils.charListFromArray(elementArr));
        line.setScore(rs.getFloat("score"));
        return line;
    }
}
