package com.poppulo;

import com.poppulo.dao.LineDao;
import com.poppulo.dao.LineInTicketDao;
import com.poppulo.dao.TicketDao;
import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import com.poppulo.entity.Ticket;
import com.poppulo.exception.TicketException;
import com.poppulo.service.TicketService;
import com.poppulo.utils.LineUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TicketServiceTest {

    @Resource
    private TicketService ticketService;

    @Resource
    private TicketDao ticketDao;

    @Resource
    private LineDao lineDao;

    @Resource
    private LineInTicketDao lineInTicketDao;

    @Test
    public void testCreate() {
        int lineCount = 3;
        // Create a ticket with 3 lines
        Ticket ticket = ticketService.create(lineCount);

        // Check if new rows have been added to table tickets, lines and lines_in_tickets
        Ticket savedTicket = ticketDao.get(ticket.getId(), false);
        Assert.assertEquals(ticket.getId(), savedTicket.getId());

        savedTicket.getLines().forEach(Line::generateId);
        Set<String> lineIds = savedTicket.getLines().stream().map(Line::getId).collect(Collectors.toSet());
        Set<String> savedLineIds = lineDao.getAll().stream().map(Line::getId).collect(Collectors.toSet());
        Assert.assertTrue(savedLineIds.containsAll(lineIds));

        List<LineInTicket> lineInTicketList = lineInTicketDao.getAll();
        Assert.assertEquals(lineCount, lineInTicketList.size());
    }

    @Test
    public void testGetAll() {
        int ticketCount = 3;
        int lineCount = 3;
        Set<UUID> ticketIds = new HashSet<UUID>();
        for(int i =0; i<ticketCount; i++) {
            Ticket ticket = ticketService.create(lineCount);
            ticketIds.add(ticket.getId());
        }

        List<Ticket> tickets = ticketService.getAll();
        Set<UUID> savedTicketIds = tickets.stream().map(Ticket::getId).collect(Collectors.toSet());
        Assert.assertTrue(savedTicketIds.containsAll(ticketIds));
    }

    @Test
    public void testGet() {
        int lineCount = 3;
        Ticket ticket = ticketService.create(lineCount);

        Ticket savedTicket = ticketService.get(ticket.getId());
        Assert.assertEquals(ticket.getId(), savedTicket.getId());
    }

    @Test
    public void testAmend() {
        int lineCount = 3;
        Ticket ticket = ticketService.create(lineCount);
        ticketService.amend(ticket.getId(), lineCount);

        Ticket savedTicket = ticketService.get(ticket.getId());
        Assert.assertEquals(2*lineCount, savedTicket.getLines().size());
    }

    @Test
    public void testAmend_checked() {
        int lineCount = 3;

        Ticket ticket = TestUtils.getTestTicket(true);
        ticketDao.save(ticket);
        lineDao.save(ticket.getLines());
        lineInTicketDao.save(LineUtils.getLineMapping(ticket.getId(), ticket.getLines()));

        // Exception is thrown if ticket is already checked
        assertThatExceptionOfType(TicketException.class)
                .isThrownBy(() -> ticketService.amend(ticket.getId(), lineCount));
    }

    @Test
    public void testCheck() {
        int lineCount = 3;

        Ticket ticket = TestUtils.getTestTicket();
        ticketDao.save(ticket);
        lineDao.save(ticket.getLines());
        lineInTicketDao.save(LineUtils.getLineMapping(ticket.getId(), ticket.getLines()));

        ticketService.checkStatus(ticket.getId());
        Ticket checkedTicket = ticketService.get(ticket.getId());

        // Verify if ticket is checked and ticket score is calculated correctly.
        Assert.assertTrue(checkedTicket.isChecked());
        Assert.assertEquals(15, checkedTicket.getTotalScore(), 0.0);
    }
}
