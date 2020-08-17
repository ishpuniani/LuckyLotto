package com.poppulo;

import com.poppulo.entity.Line;
import com.poppulo.entity.LineInTicket;
import com.poppulo.entity.Ticket;
import com.poppulo.utils.LineUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class LineUtilsTest {
    @Test
    public void testLineGenerate() {
        int lineCount = 3;
        List<Line> lines = LineUtils.generateLines(lineCount);
        Assert.assertEquals(lineCount, lines.size());
    }

    @Test
    public void testGenerateRandomLine() {
        int lineSize = 3;
        List<Character> lineValues = Arrays.asList('0','1','2');
        Line line = LineUtils.generateRandomLine(lineValues, lineSize);

        // Checking the size of lines generated
        Assert.assertEquals(lineSize, line.getElements().size());

        // Checking if the random lines generated are in range
        Set<Character> lineValuesSet = new HashSet<>(lineValues);
        Assert.assertTrue(lineValuesSet.containsAll(line.getElements()));
    }

    @Test
    public void testLineMapping() {
        String uuidStr = TestUtils.UUID_STR;
        UUID id = UUID.fromString(uuidStr);
        List<Line> lines = TestUtils.getTestLines();
        List<LineInTicket> lineInTickets = LineUtils.getLineMapping(id, lines);

        // Check that size is same as number of lines
        Assert.assertEquals(lines.size(), lineInTickets.size());

        // Check that ID is same as the input provided
        Set<UUID> idSet = lineInTickets.stream().map(LineInTicket::getTicketId).collect(Collectors.toSet());
        Assert.assertEquals(1, idSet.size());
        Assert.assertTrue(idSet.contains(id));
    }
}
