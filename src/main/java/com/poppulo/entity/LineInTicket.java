package com.poppulo.entity;

import java.util.UUID;

public class LineInTicket {
    private UUID id;
    private String lineId;
    private UUID ticketId;

    public LineInTicket(String lineId, UUID ticketId) {
        this.id = UUID.randomUUID();
        this.lineId = lineId;
        this.ticketId = ticketId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "LineInTicketDTO{" +
                "id=" + id +
                ", lineId='" + lineId + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
