package com.poppulo.entity;

import com.poppulo.TestUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TicketEntityTest {

    @Test
    public void testComputeTotalScore() {
        Ticket ticket = TestUtils.getTestTicket(false);
        ticket.computeTotalScore();
        Assert.assertEquals(15, ticket.getTotalScore(), 0.0);
    }
}
