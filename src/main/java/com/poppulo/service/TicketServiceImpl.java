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
        List<Ticket> tickets = ticketDao.getAll();
        /*for(Ticket ticket : tickets) {
            List<Line> lines = lineDao.getLinesInTicket(ticket.getId());
            ticket.setLines(lines);
        }*/

        return tickets;
    }

    @Override
    public Ticket get(UUID ticketId) {
        return ticketDao.get(ticketId, false);
    }

    @Override
    public Ticket create(int lineCount) {
        Ticket ticket = new Ticket();
        List<Line> lines = LineUtils.generateLines(lineCount);
        lineDao.save(lines);

        ticket.setLines(lines);
        ticket = ticketDao.save(ticket);

        lineInTicketDao.save(LineUtils.getLineMapping(ticket.getId(), lines));
        return get(ticket.getId());
    }

    @Override
    public Ticket amend(UUID ticketId, int lineCount) {
        Ticket ticket = ticketDao.get(ticketId, false);
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
    public Ticket checkStatus(UUID ticketId) {
        Ticket ticket = ticketDao.get(ticketId, true);
        if (ticket.isChecked()) {
            return ticket;
        }
        ticket.computeTotalScore();
        ticket.setChecked(true);
        return ticketDao.save(ticket);
    }
}
