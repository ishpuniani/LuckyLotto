package com.poppulo.dao;

import com.poppulo.TestUtils;
import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import com.poppulo.entity.Ticket;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LineInTicketDaoTest {

    @Resource
    private LineInTicketDao lineInTicketDao;

    @Resource
    private LineDao lineDao;

    @Resource
    private TicketDao ticketDao;

    @Test
    public void testSave_foreignKey() {
        UUID ticketId = UUID.randomUUID();
        List<Line> lines = LineUtils.generateLines(3);
        for (Line line : lines) {
            line.generateId();
        }

        List<LineInTicket> lineInTickets = LineUtils.getLineMapping(ticketId, lines);
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> lineInTicketDao.save(lineInTickets));
    }

    @Test
    public void testSaveAndGetAll() {
        Ticket ticket = ticketDao.save(new Ticket());
        List<Line> lines = TestUtils.getTestLines();
        lineDao.save(lines);

        List<LineInTicket> lineInTickets = LineUtils.getLineMapping(ticket.getId(), lines);
        lineInTicketDao.save(lineInTickets);

        List<LineInTicket> saved = lineInTicketDao.getAll();
        Assert.assertEquals(lineInTickets.size(), saved.size());
    }
}
