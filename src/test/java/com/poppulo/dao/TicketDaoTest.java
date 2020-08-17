package com.poppulo.dao;

import com.poppulo.dao.TicketDao;
import com.poppulo.entity.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TicketDaoTest {

    @Resource
    private TicketDao ticketDao;

    @Test
    public void testGetAndSave() {
        // Save new ticket in the database
        Ticket ticket = ticketDao.save(new Ticket());

        // Get the ticket saved and check if we are getting the same id
        Ticket savedTicket = ticketDao.get(ticket.getId(), false);
        Assert.assertEquals(ticket.getId(), savedTicket.getId());
    }

    @Test
    public void testGetAll() {
        // Checking if database is empty
        List<Ticket> ticketList = ticketDao.getAll();
        Assert.assertEquals(0, ticketList.size());

        // Adding tickets and checking if correct number has been added
        ticketDao.save(new Ticket());
        ticketDao.save(new Ticket());
        ticketDao.save(new Ticket());

        ticketList = ticketDao.getAll();
        Assert.assertEquals(3, ticketList.size());
    }

}
