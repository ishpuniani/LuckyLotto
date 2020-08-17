package com.poppulo;

import com.poppulo.entity.Line;
import com.poppulo.entity.Ticket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static String UUID_STR = "2d5ac950-e09b-11ea-87d0-0242ac130003";

    public static List<Line> getTestLines() {
        List<Line> lines = new ArrayList<Line>();
        Line line0 = new Line();
        line0.setElements(Arrays.asList('0', '0', '0'));
        line0.generateId();
        line0.computeScore();
        lines.add(line0);

        Line line1 = new Line();
        line1.setElements(Arrays.asList('1', '1', '1'));
        line1.generateId();
        line1.computeScore();
        lines.add(line1);

        Line line2 = new Line();
        line2.setElements(Arrays.asList('2', '2', '2'));
        line2.generateId();
        line2.computeScore();
        lines.add(line2);

        return lines;
    }

    public static Ticket getTestTicket() {
        return getTestTicket(false);
    }

    public static Ticket getTestTicket(boolean checked) {
        return getTestTicket(checked, false);
    }

    public static Ticket getTestTicket(boolean checked, boolean randomId) {
        Ticket ticket = new Ticket();
        ticket.setId(randomId ? UUID.randomUUID() : UUID.fromString(UUID_STR));
        ticket.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        ticket.setLines(getTestLines());
        if(checked) {
            ticket.setChecked(true);
            ticket.computeTotalScore();
        }
        return ticket;
    }
}
