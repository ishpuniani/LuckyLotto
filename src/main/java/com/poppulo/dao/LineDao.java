package com.poppulo.dao;

import com.poppulo.entity.Line;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface LineDao {
    public List<Line> getLinesInTicket(UUID ticketId, boolean withScore);

    public void save(List<Line> lines);
}
