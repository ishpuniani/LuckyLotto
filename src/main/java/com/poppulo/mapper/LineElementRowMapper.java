package com.poppulo.mapper;

import com.poppulo.entity.Line;
import com.poppulo.utils.LineUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LineElementRowMapper implements RowMapper<Line> {
    @Override
    public Line mapRow(ResultSet rs, int rowNum) throws SQLException {
        Line line = new Line();
        String [] elementArr = (String[]) rs.getArray("elements").getArray();
        line.setElements(LineUtils.charListFromArray(elementArr));
        return line;
    }
}
