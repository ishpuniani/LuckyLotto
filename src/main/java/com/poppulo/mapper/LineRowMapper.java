package com.poppulo.mapper;

import com.poppulo.entity.Line;
import com.poppulo.utils.LineUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineRowMapper implements RowMapper<Line> {
    @Override
    public Line mapRow(ResultSet rs, int rowNum) throws SQLException {
        Line line = new Line();
        line.setId(rs.getString("id"));
        line.setElements(LineUtils.getElementsFromResultSet(rs));
        line.setScore(rs.getFloat("score"));
        line.setCreatedAt(rs.getDate("created_at"));
        line.setUpdatedAt(rs.getDate("updated_at"));
        return line;
    }
}
