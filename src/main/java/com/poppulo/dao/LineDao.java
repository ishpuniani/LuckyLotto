package com.poppulo.dao;

import com.poppulo.entity.Line;

import java.util.List;
import java.util.UUID;

public interface LineDao {
    public Line create(Line lineDTO);
    public Line get(String id);
    public List<Line> getLinesInTicket(UUID ticketId);
}
