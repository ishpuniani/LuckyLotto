package com.poppulo.controller;

import com.poppulo.entity.Ticket;
import com.poppulo.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tickets", produces = "application/json; charset=UTF-8")
public class TicketController {
    private final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Resource
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getTickets() {
        return ticketService.getAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable UUID id) {
        return ticketService.get(id);
    }

    @PostMapping
    public Ticket createTicket(@RequestParam("n") int lineCount) {
        return ticketService.create(lineCount);
    }

    @PutMapping("/{id}")
    public Ticket amendTicket(@PathVariable UUID id, @RequestParam("n") int lineCount) {
        return ticketService.amend(id, lineCount);
    }

    @PutMapping("/status/{id}")
    public Ticket checkTicketStatus(@PathVariable UUID id) {
        return ticketService.checkStatus(id);
    }
}
