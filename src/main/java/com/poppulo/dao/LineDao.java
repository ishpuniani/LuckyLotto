package com.poppulo.dao;

import com.poppulo.entity.Line;

import java.util.List;
import java.util.UUID;

public interface LineDao {
    public List<Line> getLinesInTicket(UUID ticketId);

    public void save(List<Line> lines);
}
