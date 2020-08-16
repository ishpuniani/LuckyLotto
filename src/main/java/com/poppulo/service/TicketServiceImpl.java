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

    /**
     * Get all ticket objects from the database
     *
     * @return list of tickets
     */
    @Override
    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    /**
     * Get ticket data from database.
     * Ticket will contain scores only if it has been checked
     *
     * @param ticketId id of the ticket to be viewed
     * @return ticket object
     */
    @Override
    public Ticket get(UUID ticketId) {
        return ticketDao.get(ticketId, false);
    }

    /**
     * Inserts a ticket object in the tickets table
     * Inserts new lines in the lines table
     * Inserts the mapping in the lines_in_tickets table
     *
     * @param lineCount number of lines that a ticket is supposed to have.
     * @return The created ticket object
     */
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

    /**
     * Function to add new lines to a ticket
     * New lines can only be added if the ticket is not checked
     * Inserts new lines to the lines table
     * Inserts new rows to the lines_in_tickets table
     *
     * @param ticketId id of the ticket to be updated
     * @param lineCount number of lines to be added
     * @return the updated ticket
     */
    @Override
    public Ticket amend(UUID ticketId, int lineCount) {
        Ticket ticket = ticketDao.get(ticketId, false);
        if (ticket.isChecked()) {
            throw new TicketException("Cannot amend ticket, status already checked.");
        }

        List<Line> newLines = LineUtils.generateLines(lineCount);
        lineDao.save(newLines);
        lineInTicketDao.save(LineUtils.getLineMapping(ticketId, newLines));

        return get(ticket.getId());
    }

    /**
     * Function to check status of the ticket
     * Compute the total ticket score
     *
     * @param ticketId id of the ticket to be checked
     * @return the checked ticket
     */
    @Override
    public Ticket checkStatus(UUID ticketId) {
        Ticket ticket = ticketDao.get(ticketId, true);
        if (ticket.isChecked()) {
            return ticket;
        }
        ticket.computeTotalScore();
        ticket.setChecked(true);
        ticketDao.save(ticket);
        return get(ticketId);
    }
}
