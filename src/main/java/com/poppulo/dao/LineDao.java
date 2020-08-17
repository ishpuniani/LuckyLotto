package com.poppulo.dao;

import com.poppulo.entity.Line;

import java.util.List;
import java.util.UUID;

public interface LineDao {
    public List<Line> getAll();

    public List<Line> getLinesInTicket(UUID ticketId);

    public List<Line> save(List<Line> lines);
}
