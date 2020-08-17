package com.poppulo;

import com.poppulo.controller.TicketController;
import com.poppulo.entity.Ticket;
import com.poppulo.exception.TicketException;
import com.poppulo.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketService ticketService;

    @Test
    public void testGetAll() throws Exception {
        int ticketCount = 3;
        List<Ticket> allTickets = new ArrayList<>();
        for (int i = 0; i < ticketCount; i++) {
            allTickets.add(TestUtils.getTestTicket(false, true));
        }

        given(ticketService.getAll()).willReturn(allTickets);

        mvc.perform(get("/tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(ticketCount)));
    }

    @Test
    public void testGet_present() throws Exception {
        Ticket ticket = TestUtils.getTestTicket();
        given(ticketService.get(UUID.fromString(TestUtils.UUID_STR))).willReturn(ticket);

        mvc.perform(get("/tickets/" + TestUtils.UUID_STR)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TestUtils.UUID_STR)));
    }


    @Test
    public void testGet_absent() throws Exception {
        given(ticketService.get(Mockito.any())).willThrow(TicketException.class);

        UUID randomId = UUID.randomUUID();
        mvc.perform(get("/tickets/" + randomId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreate() throws Exception {
        int lineCount = 3;
        Ticket ticket = TestUtils.getTestTicket();
        given(ticketService.create(lineCount)).willReturn(ticket);

        mvc.perform(post("/tickets")
                .param("n", String.valueOf(lineCount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TestUtils.UUID_STR)));
    }

    @Test
    public void testAmend_unchecked() throws Exception {
        int lineCount = 3;
        Ticket ticket = TestUtils.getTestTicket();
        Ticket updatedTicket = TestUtils.getTestTicket();
        updatedTicket.getLines().addAll(TestUtils.getTestLines());

        given(ticketService.amend(ticket.getId(), lineCount)).willReturn(updatedTicket);

        mvc.perform(put("/tickets/" + TestUtils.UUID_STR)
                .param("n", String.valueOf(lineCount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lines", hasSize(lineCount*2)));
    }

    @Test
    public void testAmend_checked() throws Exception {
        int lineCount = 3;
        Ticket ticket = TestUtils.getTestTicket(true);
        given(ticketService.amend(ticket.getId(), lineCount)).willThrow(TicketException.class);

        mvc.perform(put("/tickets/" + TestUtils.UUID_STR)
                .param("n", String.valueOf(lineCount))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCheckStatus() throws Exception {
        Ticket ticket = TestUtils.getTestTicket();
        Ticket updatedTicket = TestUtils.getTestTicket(true);

        given(ticketService.checkStatus(ticket.getId())).willReturn(updatedTicket);

        mvc.perform(put("/tickets/status/" + TestUtils.UUID_STR)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checked", is(true)))
                .andExpect(jsonPath("$.totalScore", is(15.0)));
    }
}
