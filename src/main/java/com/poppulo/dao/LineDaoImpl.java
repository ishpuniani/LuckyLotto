package com.poppulo.dao;

import com.poppulo.entity.Line;
import com.poppulo.mapper.LineElementRowMapper;
import com.poppulo.mapper.LineRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class LineDaoImpl implements LineDao {

    private final Logger logger = LoggerFactory.getLogger(LineDaoImpl.class);

    public LineDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private NamedParameterJdbcTemplate template;

    @Override
    public List<Line> getLinesInTicket(UUID ticketId, boolean withScore) {
        String query = "";
        List<Line> lines;
        if(withScore) {
            query = "select * from lines " +
                    "where id in (select line_id from lines_in_tickets where ticket_id=:ticketId)";
        } else {
            query = "select elements from lines " +
                    "where id in (select line_id from lines_in_tickets where ticket_id=:ticketId)";
        }
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("ticketId", ticketId);

        if(withScore) {
            lines = template.query(query, param, new LineRowMapper());
        } else {
            lines = template.query(query, param, new LineElementRowMapper());
        }

        logger.info("Retrieved " + lines.size() + " lines for " + ticketId);
        return lines;
    }

    @Override
    public void save(List<Line> lines) {
        String query = "insert into lines(id, elements, score, created_at, updated_at) " +
                "values(:id, array [:elements ], :score, :createdAt, :updatedAt) " +
                "ON CONFLICT(id) DO UPDATE set score=:score, updated_at=:updatedAt";

        SqlParameterSource[] params = new SqlParameterSource[lines.size()];
        int counter = 0;

        for (Line line : lines) {
            line.generateId();
            line.computeScore();
            line.setCreatedAt(new Date(System.currentTimeMillis()));
            line.setUpdatedAt(new Date(System.currentTimeMillis()));

            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("id", line.getId())
                    .addValue("elements", line.getElements())
                    .addValue("score", line.getScore())
                    .addValue("createdAt", line.getCreatedAt())
                    .addValue("updatedAt", line.getUpdatedAt());

            params[counter++] = param;
        }

        int[] createdRows = template.batchUpdate(query, params);
        logger.info("Saved lines:: " + lines.size());
    }
}
