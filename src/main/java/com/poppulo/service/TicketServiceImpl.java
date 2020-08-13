package com.poppulo.service;

import com.poppulo.dao.TicketDao;
import com.poppulo.entity.Line;
import com.poppulo.entity.Ticket;
import com.poppulo.utils.LineUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Component
public class TicketServiceImpl implements TicketService {

    @Resource
    private TicketDao ticketDao;

    @Override
    public List<Ticket> getTickets() {
        return ticketDao.getTickets();
    }

    @Override
    public Ticket get(UUID ticketId) {
        return ticketDao.get(ticketId);
    }

    @Override
    public Ticket create(int lineCount) {
        Ticket ticket = new Ticket();
        List<Line> lines = LineUtils.generateLines(lineCount);
        ticket.setLines(lines);
        return ticketDao.create(ticket);
    }

    @Override
    public Ticket amend(UUID ticketId, int lineCount) {
        Ticket ticket = ticketDao.get(ticketId);
        if(!ticket.isChecked()) {
            List<Line> newLines = LineUtils.generateLines(lineCount);
            ticket.getLines().addAll(newLines);
            ticket = ticketDao.update(ticket);
        }
        return ticket;
    }

    @Override
    public Ticket checkStatus(UUID id) {
        Ticket ticket = ticketDao.get(id);
        if(ticket.isChecked()) {
            return ticket;
        }
        ticket.computeTotalScore();
        ticket.setChecked(true);
        return ticketDao.update(ticket);
    }
}
