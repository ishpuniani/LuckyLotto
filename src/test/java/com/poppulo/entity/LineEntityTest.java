package com.poppulo.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class LineEntityTest {

    @Test
    public void testGenerateId() {
        Line line = new Line();
        line.setElements(Arrays.asList('0','0','0'));
        line.generateId();
        Assert.assertEquals("000", line.getId());

        line.setElements(Arrays.asList('1','2','1'));
        line.generateId();
        Assert.assertEquals("121", line.getId());
    }

    @Test
    public void testLineComputeScore() {
        // first case : sum = 2, score = 10
        Line line = new Line();
        line.setElements(Arrays.asList('1','1','0'));
        line.computeScore();
        Assert.assertEquals(10.0, line.getScore(), 0.0);

        // second case : all elements same, score = 5
        line.setElements(Arrays.asList('1','1','1'));
        line.computeScore();
        Assert.assertEquals(5, line.getScore(), 0.0);

        // third case : 2nd and 3rd numbers are different from the 1st, score = 1
        line.setElements(Arrays.asList('0','1','2'));
        line.computeScore();
        Assert.assertEquals(1, line.getScore(), 0.0);

        // final case : otherwise, result 0
        line.setElements(Arrays.asList('2','1','2'));
        line.computeScore();
        Assert.assertEquals(0, line.getScore(), 0.0);
    }
}
