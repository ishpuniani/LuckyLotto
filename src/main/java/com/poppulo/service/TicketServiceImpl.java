package com.poppulo.service;

import com.poppulo.dao.LineDao;
import com.poppulo.dao.LineInTicketDao;
import com.poppulo.dao.TicketDao;
import com.poppulo.entity.Line;
import com.poppulo.entity.Ticket;
import com.poppulo.exception.TicketException;
import com.poppulo.utils.LineUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Component
public class TicketServiceImpl implements TicketService {

    @Resource
    private TicketDao ticketDao;

    @Resource
    private LineDao lineDao;

    @Resource
    private LineInTicketDao lineInTicketDao;

    @Override
    public List<Ticket> getTickets() {
        return ticketDao.getTickets();
    }

    @Override
    public Ticket get(UUID ticketId) {
        Ticket ticket = ticketDao.get(ticketId);
        List<Line> lines = lineDao.getLinesInTicket(ticketId, false);
        ticket.setLines(lines);

        return ticket;
    }

    @Override
    public Ticket create(int lineCount) {
        Ticket ticket = new Ticket();
        List<Line> lines = LineUtils.generateLines(lineCount);
        lineDao.save(lines);

        ticket.setLines(lines);
        ticket = ticketDao.create(ticket);

        lineInTicketDao.save(LineUtils.getLineMapping(ticket.getId(), lines));
        return get(ticket.getId());
    }

    @Override
    public Ticket amend(UUID ticketId, int lineCount) {
        Ticket ticket = ticketDao.get(ticketId);
        if (ticket.isChecked()) {
            throw new TicketException("Cannot amend ticket, status already checked.");
        }

        List<Line> newLines = LineUtils.generateLines(lineCount);
        lineDao.save(newLines);

        ticket.getLines().addAll(newLines);
        lineInTicketDao.save(LineUtils.getLineMapping(ticketId, newLines));

        return get(ticket.getId());
    }

    @Override
    public Ticket checkStatus(UUID id) {
        Ticket ticket = ticketDao.get(id);
        if (ticket.isChecked()) {
            return ticket;
        }
        ticket.computeTotalScore();
        ticket.setChecked(true);
        return ticketDao.update(ticket);
    }
}
