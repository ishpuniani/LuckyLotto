package com.poppulo.entity;

import java.util.UUID;

public class LineInTicket {
    private String lineId;
    private UUID ticketId;

    public LineInTicket(String lineId, UUID ticketId) {
        this.lineId = lineId;
        this.ticketId = ticketId;
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
        return "LineInTicket{" +
                "lineId='" + lineId + '\'' +
                ", ticketId=" + ticketId +
                '}';
    }
}
